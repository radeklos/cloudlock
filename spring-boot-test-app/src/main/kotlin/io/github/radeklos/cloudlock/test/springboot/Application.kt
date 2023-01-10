package io.github.radeklos.cloudlock.test.springboot

import io.github.radeklos.cloudlock.spring.annotation.CloudLock
import io.github.radeklos.cloudlock.spring.annotation.EnableCloudLock
import mu.KotlinLogging
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.lang.Thread.sleep
import java.time.Instant

@EnableCloudLock
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

    open class ScheduledBean {

        private var log = KotlinLogging.logger { }

        @CloudLock
        @Scheduled(fixedRate = 5_000)
        open fun scheduler() {
            sleep(2_000)
            log.info { "scheduler tick, at=${Instant.now()}" }
        }
    }
}
