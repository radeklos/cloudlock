package com.github.radeklos.cloudlock.spring.aop

import com.github.radeklos.cloudlock.spring.core.CloudLockConfigurationExtractor
import mu.KotlinLogging
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation

class LockInterceptor(
    private var lockingExecutor: LockingExecutor,
    private var lockConfigurationExtractor: CloudLockConfigurationExtractor,
) : MethodInterceptor {

    private var log = KotlinLogging.logger { }

    override fun invoke(invocation: MethodInvocation): Any? {
        val config = lockConfigurationExtractor.extractConfiguration(invocation.method)
        val result = lockingExecutor.execute(invocation::proceed, config)
        log.info { "invocation finished, lock-name=${config.name} executed=${result.executed}" }
        return result
    }
}
