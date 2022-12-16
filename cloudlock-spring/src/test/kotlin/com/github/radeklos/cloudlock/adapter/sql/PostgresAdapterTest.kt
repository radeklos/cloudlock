package com.github.radeklos.cloudlock.adapter.sql

import com.github.radeklos.cloudlock.SpringBootTestApplication
import com.github.radeklos.cloudlock.adapter.LockState
import com.github.radeklos.cloudlock.adapter.sql.JooqRepository.Companion.IS_LOCKED
import com.github.radeklos.cloudlock.adapter.sql.JooqRepository.Companion.LOCK_NAME
import com.github.radeklos.cloudlock.spring.core.CloudLockConfig
import io.kotest.assertions.timing.eventually
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.jooq.impl.DSL
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.sql.DataSource
import kotlin.time.Duration.Companion.seconds

@SpringBootTest(classes = [SpringBootTestApplication::class])
class PostgresAdapterTest(var dataSource: DataSource) : FunSpec({

    val repository = JooqRepository(dataSource)
    val adapter = PostgresAdapter(repository)
    var random = ""

    fun getIsLocked(lockName: String): Boolean? {
        return repository.dslContext.selectFrom(repository.table)
            .where(DSL.field(LOCK_NAME).eq(lockName))
            .fetchOne(DSL.field(IS_LOCKED, Boolean::class.java))
    }

    beforeTest {
        repository.createSchema()
    }

    beforeEach {
        random = UUID.randomUUID().toString()
    }

    test("should release one record in table") {
        adapter.getStateAndLockWhenUnlocked(CloudLockConfig("lock1_$random"), "hostname")
        adapter.getStateAndLockWhenUnlocked(CloudLockConfig("lock2_$random"), "hostname")

        adapter.unlock(CloudLockConfig("lock1_$random"))

        getIsLocked("lock1_$random") shouldBe false
        getIsLocked("lock2_$random") shouldBe true
    }

    test("non existing lock should be un locked on creation and state should return unlocked") {
        val state = adapter.getStateAndLockWhenUnlocked(CloudLockConfig("lock1_$random"), "hostname")

        state shouldBe LockState.UNLOCKED
        getIsLocked("lock1_$random") shouldBe true
    }

    test("getting first state should be unlocked and second should be locked") {
        val firstCall = adapter.getStateAndLockWhenUnlocked(CloudLockConfig("lock1_$random"), "hostname")
        val secondCall = adapter.getStateAndLockWhenUnlocked(CloudLockConfig("lock1_$random"), "hostname")

        firstCall shouldBe LockState.UNLOCKED
        secondCall shouldBe LockState.LOCKED
    }

    test("when getting state on unlocked lock should be false") {
        val firstCall = adapter.getStateAndLockWhenUnlocked(CloudLockConfig("lock1_$random"), "hostname")
        adapter.unlock(CloudLockConfig("lock1_$random"))

        val secondCall = adapter.getStateAndLockWhenUnlocked(CloudLockConfig("lock1_$random"), "hostname")

        firstCall shouldBe LockState.UNLOCKED
        secondCall shouldBe LockState.UNLOCKED
    }

    test("should lock only once ") {
        val es: ExecutorService = Executors.newFixedThreadPool(10)
        val callables: MutableList<CompletableFuture<LockState>> = ArrayList()
        repeat(10) {
            callables.add(CompletableFuture.supplyAsync({
                adapter.getStateAndLockWhenUnlocked(CloudLockConfig("lock1_$random"), "hostname")
            }, es))
        }

        eventually(5.seconds) {
            CompletableFuture.allOf(*callables.toTypedArray()).isDone
        }

        callables.filter { it.get() == LockState.UNLOCKED } shouldHaveSize 1
        callables.filter { it.get() == LockState.LOCKED } shouldHaveSize 9
    }

})
