package com.github.radeklos.cloudlock.spring.core

import com.github.radeklos.cloudlock.spring.annotation.CloudLock
import java.lang.reflect.Method
import org.springframework.core.annotation.AnnotatedElementUtils

class CloudLockConfigurationExtractor {

    fun extractConfiguration(method: Method): CloudLockConfig {
        val annotation = getAnnotation(method)
        return CloudLockConfig(
            name = annotation.name
        )
    }

    private fun getAnnotation(method: Method): CloudLock {
        return AnnotatedElementUtils.getMergedAnnotation(method, CloudLock::class.java) ?:
            throw CloudLockConfigurationExtractorException("Cannot find `CloudLock` annotation")
    }
}

data class CloudLockConfig(
    var name: String
)

class CloudLockConfigurationExtractorException(message: String) : Throwable(message)
