package com.github.radeklos.cloudlock.adapter

import com.github.radeklos.cloudlock.spring.core.CloudLockConfig

interface Adapter {

    fun getStateAndLockWhenUnlocked(config: CloudLockConfig): Boolean

    fun unlock(config: CloudLockConfig)

}
