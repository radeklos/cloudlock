package io.github.radeklos.cloudlock.spring.core

import io.github.radeklos.cloudlock.spring.annotation.CloudLock
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mu.KotlinLogging


private class ConfigurationExtractorTestClass {

    private var log = KotlinLogging.logger { }

    @CloudLock
    fun withoutName() {
        log.info { "withoutName" }
    }

    @CloudLock(name = "some custom name")
    fun withName() {
        log.info { "withName" }
    }

}

class CloudLockConfigurationExtractorTest : FunSpec({

    test("should populate get name of method when no lock name is provided") {
        val method = ConfigurationExtractorTestClass::class.java.getMethod("withoutName")
        val config = CloudLockConfigurationExtractor().extractConfiguration(method)

        config.name shouldBe "com.github.radeklos.cloudlock.spring.core.ConfigurationExtractorTestClass.withoutName"
    }

    test("should pupulate with provided name") {
        val method = ConfigurationExtractorTestClass::class.java.getMethod("withName")
        val config = CloudLockConfigurationExtractor().extractConfiguration(method)

        config.name shouldBe "some custom name"
    }

})
