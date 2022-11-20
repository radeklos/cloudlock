package com.github.radeklos.cloudlock.test.springboot

import com.github.radeklos.cloudlock.spring.annotation.CloudLock
import com.github.radeklos.cloudlock.spring.annotation.EnableCloudLock
import mu.KotlinLogging
import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@EnableCloudLock
@EnableScheduling
@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
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

    open class ScheduledBean {
        var log = KotlinLogging.logger {  }
        @CloudLock
        @Scheduled(fixedRate = 5_000)
        open fun scheduler() {
            log.info { "scheduler tick" }
            SchedulerMemory.add()
        }
    }
}