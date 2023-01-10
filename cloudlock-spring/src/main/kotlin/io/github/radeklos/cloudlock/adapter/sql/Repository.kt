package io.github.radeklos.cloudlock.adapter.sql

interface Repository {
    fun createSchema()
    fun unlock(lockName: String, hostname: String)
    fun lock(lockName: String, hostname: String)
}
