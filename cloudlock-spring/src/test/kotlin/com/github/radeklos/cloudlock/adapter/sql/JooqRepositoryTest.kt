package com.github.radeklos.cloudlock.adapter.sql

import com.github.radeklos.cloudlock.SpringBootTestApplication
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import javax.sql.DataSource

@SpringBootTest(classes = [SpringBootTestApplication::class])
class JooqRepositoryTest(dataSource: DataSource) : FunSpec({

    var repository: JooqRepository

    test("should create lock record") {
        repository = JooqRepository(dataSource)
        repository.createSchema()
        repository.lock("lock_name", "hostname")

        repository.isLocked("lock_name") shouldBe true
    }

    test("should return false for unlock records") {
        repository = JooqRepository(dataSource)
        repository.createSchema()

        repository.lock("lock_name", "hostname")
        repository.unlock("lock_name", "hostname")

        repository.isLocked("lock_name") shouldBe false
    }

    test("should return false for non existing lock") {
        repository = JooqRepository(dataSource)
        repository.createSchema()

        repository.isLocked("not exists") shouldBe false
    }

})
