package com.github.radeklos.cloudlock.adapter.sql

import org.jooq.DSLContext
import org.jooq.conf.RenderNameCase
import org.jooq.conf.RenderQuotedNames
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DSL.constraint
import org.jooq.impl.SQLDataType.BOOLEAN
import org.jooq.impl.SQLDataType.INTEGER
import org.jooq.impl.SQLDataType.TIMESTAMP
import org.jooq.impl.SQLDataType.VARCHAR
import javax.sql.DataSource

class JooqRepository(dataSource: DataSource) : Repository {

    val dslContext: DSLContext
    val table = DSL.table(TABLE_NAME)

    companion object {
        const val TABLE_NAME = "cloud_lock"
        const val ID = "id"
        const val LOCK_NAME = "lock_name"
        const val UPDATED_BY = "updated_by"
        const val IS_LOCKED = "is_locked"
        const val UPDATED_AT = "updated_at"
    }

    init {
        val connection = dataSource.connection
        dslContext = DSL.using(
            connection, Settings()
                .withRenderQuotedNames(RenderQuotedNames.ALWAYS)
                .withRenderNameCase(RenderNameCase.AS_IS)
        )
    }

    override fun createSchema() {
        dslContext.createTableIfNotExists(table)
            .column(ID, INTEGER.identity(true))
            .column(LOCK_NAME, VARCHAR)
            .column(UPDATED_BY, VARCHAR)
            .column(IS_LOCKED, BOOLEAN)
            .column(UPDATED_AT, TIMESTAMP)
            .constraints(
                constraint("lock_table_pk").primaryKey(ID),
                constraint("cloud_lock_unique").unique(LOCK_NAME)
            ).execute()
    }

    override fun lock(lockName: String, hostname: String) {
        createLockedRecord(lockName, hostname, true)
    }

    override fun unlock(lockName: String, hostname: String) {
        createLockedRecord(lockName, hostname, false)
    }

    fun isLocked(lockName: String): Boolean {
        return dslContext.select(DSL.field("locked"))
            .from(table)
            .where(DSL.field("lock_name").eq(lockName))
            .limit(1)
            .fetchOne(DSL.field("locked", Boolean::class.java)) ?: false
    }

    private fun createLockedRecord(lockName: String, hostname: String, lock: Boolean) {
        val insert = dslContext.insertInto(
            table,
            DSL.field("lock_name"),
            DSL.field("updated_by"),
            DSL.field("locked"),
            DSL.field("updated_at")
        )
            .values(lockName, hostname, lock, DSL.now())
            .onDuplicateKeyIgnore()
            .execute()

        if (insert == 0) {
            dslContext.update(table)
                .set(DSL.field("locked"), lock)
                .set(DSL.field("updated_at"), DSL.now())
                .where(
                    DSL.field("lock_name").eq(lockName),
                    DSL.field("updated_by").eq(hostname)
                ).execute()
        }
    }

}
