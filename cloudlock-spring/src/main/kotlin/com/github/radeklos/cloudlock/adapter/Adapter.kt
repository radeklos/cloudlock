package com.github.radeklos.cloudlock.adapter

import com.github.radeklos.cloudlock.spring.core.CloudLockConfig

interface Adapter {

    fun getStateAndLockWhenUnlocked(config: CloudLockConfig, hostname: String): LockState

    fun unlock(config: CloudLockConfig)

}

enum class LockState {
    LOCKED, UNLOCKED
}
