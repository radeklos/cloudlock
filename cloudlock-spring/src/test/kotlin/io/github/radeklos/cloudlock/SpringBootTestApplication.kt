package io.github.radeklos.cloudlock

import io.github.radeklos.cloudlock.spring.annotation.EnableCloudLock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableCloudLock
@EnableScheduling
@SpringBootApplication
class SpringBootTestApplication
