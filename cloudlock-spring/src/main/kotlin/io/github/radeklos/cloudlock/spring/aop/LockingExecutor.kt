package io.github.radeklos.cloudlock.spring.aop

import io.github.radeklos.cloudlock.adapter.Adapter
import io.github.radeklos.cloudlock.adapter.LockState
import io.github.radeklos.cloudlock.spring.core.CloudLockConfig
import mu.KotlinLogging
import java.net.InetAddress

class LockingExecutor(var adapter: Adapter) {

    private var log = KotlinLogging.logger { }
    private var hostName = InetAddress.getLocalHost().hostName

    fun <T> execute(task: TaskWithResult<T>, config: CloudLockConfig): TaskResult<T> {
        if (adapter.getStateAndLockWhenUnlocked(
                config,
                hostName
            ) == LockState.UNLOCKED
        ) {
            try {
                log.info { "task executed, hostname=$hostName lock-name=${config.name}" }
                return TaskResult.executed(task.call())
            } finally {
                adapter.unlock(config)
                log.info { "task was unlocked, hostname=$hostName lock-name=${config.name}" }
            }
        }
        return TaskResult.notExecuted()
    }

}


fun interface TaskWithResult<T> {
    fun call(): T
}

data class TaskResult<T> private constructor(
    val executed: Boolean = false,
    val result: T? = null
) {

    companion object {
        fun <T> executed(result: T?): TaskResult<T> {
            return TaskResult(true, result)
        }

        fun <T> notExecuted(): TaskResult<T> {
            return TaskResult(false, null)
        }
    }
}
