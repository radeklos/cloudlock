package com.github.radeklos.cloudlock.test.springboot

import mu.KotlinLogging
import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@EnableScheduling
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    SpringApplicationBuilder()
        .bannerMode(Banner.Mode.OFF)
        .sources(Application::class.java)
        .run(*args)
}

@Configuration
class ScheduledConfig {


    @Bean
    fun scheduledBean(): ScheduledBean {
        return ScheduledBean()
    }

    class ScheduledBean {
        var log = KotlinLogging.logger {  }
        @Scheduled(fixedRate = 5)
        fun scheduler() {
            log.info { "scheduler tick" }
            SchedulerMemory.add()
        }
    }
}