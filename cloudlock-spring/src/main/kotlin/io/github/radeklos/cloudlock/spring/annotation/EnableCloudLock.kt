package io.github.radeklos.cloudlock.spring.annotation

import io.github.radeklos.cloudlock.spring.configuration.MethodProxyLockConfiguration
import io.github.radeklos.cloudlock.spring.configuration.PostgresAdapterConfiguration
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(value = [PostgresAdapterConfiguration::class, MethodProxyLockConfiguration::class])
annotation class EnableCloudLock
