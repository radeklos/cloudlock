package com.github.radeklos.cloudlock.test.springboot

import io.kotest.assertions.timing.eventually
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import kotlin.time.Duration.Companion.seconds


@AutoConfigureMockMvc
@SpringBootTest(classes = [Application::class])
class SchedulerTest : FunSpec({

    beforeEach {
        SchedulerMemory.clean()
    }

    test("should ping controller") {
        eventually(6.seconds) {
            SchedulerMemory.data shouldHaveAtLeastSize 1
        }
    }
})