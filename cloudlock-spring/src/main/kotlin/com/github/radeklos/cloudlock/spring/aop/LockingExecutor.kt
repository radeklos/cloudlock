package com.github.radeklos.cloudlock.spring.aop

import com.github.radeklos.cloudlock.adapter.Adapter
import com.github.radeklos.cloudlock.adapter.LockState
import com.github.radeklos.cloudlock.spring.core.CloudLockConfig

class LockingExecutor(var adapter: Adapter) {

    fun <T> execute(task: TaskWithResult<T>, config: CloudLockConfig): TaskResult<T> {
        if (adapter.getStateAndLockWhenUnlocked(config, "hostname") == LockState.UNLOCKED) {
            try {
                return TaskResult.executed(task.call())
            } finally {
                adapter.unlock(config)
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
            return TaskResult(true, null)
        }
    }
}
