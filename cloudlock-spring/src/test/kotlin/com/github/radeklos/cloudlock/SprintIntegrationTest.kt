package com.github.radeklos.cloudlock

import io.kotest.assertions.timing.eventually
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.time.Instant
import kotlin.time.Duration.Companion.seconds


@SpringJUnitConfig(ScheduledConfig::class)
class SprintIntegrationTest : FunSpec({
    test("increment") {
        eventually(6.seconds) {
            SchedulerMemory.data shouldHaveSize 1
        }
    }
})


class ScheduledConfig {

    @Bean
    fun scheduledBean(): ScheduledBean {
        return ScheduledBean()
    }

    class ScheduledBean {
        @Scheduled(fixedRate = 5)
        fun scheduler() {
            SchedulerMemory.add()
        }
    }
}

object SchedulerMemory{
    private val mutableData: MutableList<Instant> = mutableListOf()

    val data: List<Instant>
        get() = this.mutableData.toList()

    fun add() {
        mutableData.add(Instant.now())
    }

}