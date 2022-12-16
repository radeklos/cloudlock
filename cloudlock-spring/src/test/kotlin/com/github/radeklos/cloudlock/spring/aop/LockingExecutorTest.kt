package com.github.radeklos.cloudlock.spring.aop

import com.github.radeklos.cloudlock.adapter.InMemoryAdapter
import com.github.radeklos.cloudlock.spring.core.CloudLockConfig
import io.kotest.assertions.timing.eventually
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration.Companion.seconds


internal class LockingExecutorTest(private var adapter: InMemoryAdapter = InMemoryAdapter()) : FunSpec({

    val total = 1_000
    val es: ExecutorService = Executors.newFixedThreadPool(10)

    test("should execute task only once") {
        val task = Task()
        val lockingExecutor = LockingExecutor(adapter)

        val callables: MutableList<CompletableFuture<TaskResult<Unit>>> = ArrayList()
        repeat(total) {
            callables.add(CompletableFuture.supplyAsync({
                lockingExecutor.execute(
                    { task.run() },
                    CloudLockConfig("test")
                )
            }, es))
        }

        eventually(5.seconds) {
            CompletableFuture.allOf(*callables.toTypedArray()).isDone
        }

        task.increment.get() shouldBe 1
    }

    test("should be able to lock task asynchronously") {
        val task1 = Task()
        val task2 = Task()
        val lockingExecutor = LockingExecutor(adapter)

        val callables: MutableList<CompletableFuture<TaskResult<Unit>>> = ArrayList()
        repeat(total) {
            callables.add(CompletableFuture.supplyAsync({
                lockingExecutor.execute(
                    { task1.run() },
                    CloudLockConfig("lock1")
                )
            }, es))
            callables.add(CompletableFuture.supplyAsync({
                lockingExecutor.execute(
                    { task2.run() },
                    CloudLockConfig("lock2")
                )
            }, es))
        }

        eventually(5.seconds) {
            CompletableFuture.allOf(*callables.toTypedArray()).isDone
        }

        task1.increment.get() shouldBe 1
        task2.increment.get() shouldBe 1
    }
})

class Task {

    val increment = AtomicInteger()

    fun run() {
        increment.getAndIncrement()
        Thread.sleep(500)
    }

}