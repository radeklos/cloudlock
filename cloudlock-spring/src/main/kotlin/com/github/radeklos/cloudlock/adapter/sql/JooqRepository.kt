package com.github.radeklos.cloudlock.adapter.sql

import org.jooq.DSLContext
import org.jooq.conf.RenderNameCase
import org.jooq.conf.RenderQuotedNames
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DSL.constraint
import org.jooq.impl.SQLDataType.*
import javax.sql.DataSource

class JooqRepository(dataSource: DataSource) : Repository {

    private val dslContext: DSLContext
    private val tableName = "cloud_lock"

    init {
        val connection = dataSource.connection
        dslContext = DSL.using(
            connection, Settings()
                .withRenderQuotedNames(RenderQuotedNames.ALWAYS)
                .withRenderNameCase(RenderNameCase.AS_IS)
        )
    }

    override fun createSchema() {
        dslContext.createTableIfNotExists(DSL.table(tableName))
            .column("id", INTEGER.identity(true))
            .column("lock_name", VARCHAR)
            .column("updated_by", VARCHAR)
            .column("locked", BOOLEAN)
            .column("updated_at", TIMESTAMP)
            .constraints(
                constraint("lock_table_pk").primaryKey("id"),
                constraint("cloud_lock_unique").unique("lock_name")
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
            .from(DSL.table(tableName))
            .where(DSL.field("lock_name").eq(lockName))
            .limit(1)
            .fetchOne(DSL.field("locked", Boolean::class.java)) ?: false
    }

    private fun createLockedRecord(lockName: String, hostname: String, lock: Boolean) {
        val insert = dslContext.insertInto(
            DSL.table(tableName),
            DSL.field("lock_name"),
            DSL.field("updated_by"),
            DSL.field("locked"),
            DSL.field("updated_at")
        )
            .values(lockName, hostname, lock, DSL.now())
            .onDuplicateKeyIgnore()
            .execute()

        if (insert == 0) {
            dslContext.update(DSL.table(tableName))
                .set(DSL.field("locked"), lock)
                .set(DSL.field("updated_at"), DSL.now())
                .where(
                    DSL.field("lock_name").eq(lockName),
                    DSL.field("updated_by").eq(hostname)
                ).execute()
        }
    }

}
