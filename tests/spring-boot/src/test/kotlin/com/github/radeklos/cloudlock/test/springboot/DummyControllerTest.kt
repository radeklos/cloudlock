package com.github.radeklos.cloudlock.test.springboot

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@AutoConfigureMockMvc
@SpringBootTest(classes = [Application::class])
class DummyControllerTest(var mockMvc: MockMvc) : FunSpec({
    test("should ping controller") {
        mockMvc.get("/dummy")
            .andExpect {
                status { isOk() }
            }
    }
})
