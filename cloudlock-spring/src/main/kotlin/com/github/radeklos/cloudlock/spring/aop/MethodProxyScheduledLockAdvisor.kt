package com.github.radeklos.cloudlock.spring.aop

import com.github.radeklos.cloudlock.spring.annotation.CloudLock
import org.aopalliance.aop.Advice
import org.springframework.aop.Pointcut
import org.springframework.aop.support.AbstractPointcutAdvisor
import org.springframework.aop.support.ComposablePointcut
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut

class MethodProxyScheduledLockAdvisor : AbstractPointcutAdvisor() {

    override fun getAdvice(): Advice {
        return LockInterceptor()
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