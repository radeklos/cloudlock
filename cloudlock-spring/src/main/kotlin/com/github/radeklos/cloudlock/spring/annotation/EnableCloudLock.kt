package com.github.radeklos.cloudlock.spring.annotation

import com.github.radeklos.cloudlock.spring.configuration.MethodProxyLockConfiguration
import com.github.radeklos.cloudlock.spring.configuration.PostgresAdapterConfiguration
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(value = [PostgresAdapterConfiguration::class, MethodProxyLockConfiguration::class])
annotation class EnableCloudLock
