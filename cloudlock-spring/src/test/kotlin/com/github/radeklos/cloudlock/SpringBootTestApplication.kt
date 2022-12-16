package com.github.radeklos.cloudlock

import com.github.radeklos.cloudlock.spring.annotation.EnableCloudLock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableCloudLock
@EnableScheduling
@SpringBootApplication
class SpringBootTestApplication
