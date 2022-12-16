package com.github.radeklos.cloudlock.test.springboot

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.time.Instant

@Controller
class DummyController {

    @GetMapping("/dummy")
    fun returnSomething(): String {
        return "I'm dummy ${Instant.now()}"
    }

}
