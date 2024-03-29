package io.github.radeklos.cloudlock.adapter

import io.github.radeklos.cloudlock.adapter.LockState.LOCKED
import io.github.radeklos.cloudlock.adapter.LockState.UNLOCKED
import io.github.radeklos.cloudlock.spring.core.CloudLockConfig

class InMemoryAdapter : Adapter {

    @Volatile
    private var lock = mutableMapOf<String, LockState>()

    @Synchronized
    override fun getStateAndLockWhenUnlocked(
        config: CloudLockConfig,
        hostName: String
    ): LockState {
        return if (lock.containsKey(config.name)) {
            lock[config.name]!!
        } else {
            lock[config.name] = UNLOCKED
            LOCKED
        }
    }

    override fun unlock(config: CloudLockConfig) {
        lock[config.name] = UNLOCKED
    }
}
