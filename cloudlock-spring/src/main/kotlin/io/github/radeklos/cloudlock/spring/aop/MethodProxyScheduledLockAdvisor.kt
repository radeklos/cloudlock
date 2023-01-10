package io.github.radeklos.cloudlock.spring.aop

import io.github.radeklos.cloudlock.spring.annotation.CloudLock
import io.github.radeklos.cloudlock.spring.core.CloudLockConfigurationExtractor
import org.aopalliance.aop.Advice
import org.springframework.aop.Pointcut
import org.springframework.aop.support.AbstractPointcutAdvisor
import org.springframework.aop.support.ComposablePointcut
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut

class MethodProxyScheduledLockAdvisor(
    private var lockingExecutor: LockingExecutor,
    private var lockConfigurationExtractor: CloudLockConfigurationExtractor,
) : AbstractPointcutAdvisor() {

    override fun getAdvice(): Advice {
        return LockInterceptor(lockingExecutor, lockConfigurationExtractor)
    }

    override fun getPointcut(): Pointcut {
        return ComposablePointcut(methodPointcutFor(CloudLock::class.java))
    }

    private fun methodPointcutFor(methodAnnotationType: Class<out Annotation>): AnnotationMatchingPointcut {
        return AnnotationMatchingPointcut(
            null,
            methodAnnotationType,
            true
        )
    }
}
