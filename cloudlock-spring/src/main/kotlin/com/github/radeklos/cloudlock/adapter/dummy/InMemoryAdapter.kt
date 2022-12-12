package com.github.radeklos.cloudlock.adapter.dummy

import com.github.radeklos.cloudlock.adapter.Adapter
import com.github.radeklos.cloudlock.spring.core.CloudLockConfig

class InMemoryAdapter : Adapter {

    @Volatile
    private var lock = mutableMapOf<String, Boolean>()

    @Synchronized
    override fun getStateAndLockWhenUnlocked(config: CloudLockConfig): Boolean {
        return if (lock.containsKey(config.name)) {
            lock[config.name]!!
        } else {
            lock[config.name] = true
            false
        }
    }

    override fun unlock(config: CloudLockConfig) {
        lock[config.name] = false
    }
}