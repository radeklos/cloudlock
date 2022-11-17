package com.github.radeklos.cloudlock.test.springboot

import java.time.Instant

object SchedulerMemory{
    private val mutableData: MutableList<Instant> = mutableListOf()

    val data: List<Instant>
        get() = this.mutableData.toList()

    fun add() {
        mutableData.add(Instant.now())
    }

    fun clean() {
        mutableData.clear()
    }

}