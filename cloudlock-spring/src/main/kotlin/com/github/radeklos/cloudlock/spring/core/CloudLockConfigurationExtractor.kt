package com.github.radeklos.cloudlock.spring.core

import com.github.radeklos.cloudlock.spring.annotation.CloudLock
import org.springframework.core.annotation.AnnotatedElementUtils
import java.lang.reflect.Method

class CloudLockConfigurationExtractor {

    fun extractConfiguration(method: Method): CloudLockConfig {
        val annotation = getAnnotation(method)
        return CloudLockConfig(
            name = annotation.name.ifBlank { "${method.declaringClass.canonicalName}.${method.name}" }
        )
    }

    private fun getAnnotation(method: Method): CloudLock {
        return AnnotatedElementUtils.getMergedAnnotation(method, CloudLock::class.java)
            ?: throw CloudLockConfigurationExtractorException("Cannot find `CloudLock` annotation")
    }
}

data class CloudLockConfig(
    var name: String
)

class CloudLockConfigurationExtractorException(message: String) : Throwable(message)
