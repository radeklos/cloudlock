package com.github.radeklos.cloudlock.adapter.dummy

import com.github.radeklos.cloudlock.adapter.Adapter

class DummyAdapter : Adapter {

    var lock = false

    override fun isLocked(): Boolean {
        return lock
    }

    override fun lock() {
        lock = true
    }

    override fun unlock() {
        lock = false
    }
}