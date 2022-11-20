package com.github.radeklos.cloudlock.adapter

interface Adapter {

    fun isLocked(): Boolean

    fun lock()

    fun unlock()

}
