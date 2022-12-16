package com.github.radeklos.cloudlock.adapter.sql

import com.github.radeklos.cloudlock.adapter.Adapter
import com.github.radeklos.cloudlock.adapter.LockState
import com.github.radeklos.cloudlock.adapter.sql.JooqRepository.Companion.IS_LOCKED
import com.github.radeklos.cloudlock.adapter.sql.JooqRepository.Companion.LOCK_NAME
import com.github.radeklos.cloudlock.adapter.sql.JooqRepository.Companion.UPDATED_AT
import com.github.radeklos.cloudlock.adapter.sql.JooqRepository.Companion.UPDATED_BY
import com.github.radeklos.cloudlock.spring.core.CloudLockConfig
import org.jooq.impl.DSL

class PostgresAdapter(private var repository: JooqRepository) : Adapter {
    override fun getStateAndLockWhenUnlocked(config: CloudLockConfig, hostname: String): LockState {
        val inserted = repository.dslContext.insertInto(repository.table,
            DSL.field(LOCK_NAME),
            DSL.field(UPDATED_BY),
            DSL.field(IS_LOCKED),
            DSL.field(UPDATED_AT)
        )
            .values(config.name, hostname, true, DSL.now())
            .onConflict(DSL.field(LOCK_NAME)).doNothing()
            .execute()

        return if (inserted == 0) {
            val updated = repository.dslContext.update(repository.table)
                .set(DSL.field(IS_LOCKED), true)
                .set(DSL.field(UPDATED_AT), DSL.now())
                .where(
                    DSL.field(LOCK_NAME).eq(config.name),
                    DSL.field(IS_LOCKED).eq(false)
                ).execute()

            if (updated == 0) LockState.LOCKED else LockState.UNLOCKED
        } else {
            LockState.UNLOCKED
        }
    }

    override fun unlock(config: CloudLockConfig) {
        repository.dslContext.update(repository.table)
            .set(DSL.field(IS_LOCKED), false)
            .where(DSL.field(LOCK_NAME).eq(config.name))
            .execute()
    }
}