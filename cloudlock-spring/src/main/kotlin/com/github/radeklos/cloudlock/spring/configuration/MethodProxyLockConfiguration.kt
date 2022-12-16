package com.github.radeklos.cloudlock.spring.configuration

import com.github.radeklos.cloudlock.adapter.Adapter
import com.github.radeklos.cloudlock.adapter.InMemoryAdapter
import com.github.radeklos.cloudlock.spring.aop.LockingExecutor
import com.github.radeklos.cloudlock.spring.aop.MethodProxyScheduledLockAdvisor
import com.github.radeklos.cloudlock.spring.core.CloudLockConfigurationExtractor
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Role

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
class MethodProxyLockConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    fun adapter(): Adapter {
        return InMemoryAdapter()
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    fun lockingExecutor(adapter: Adapter): LockingExecutor {
        return LockingExecutor(adapter)
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    fun cloudLockConfigurationExtractor(): CloudLockConfigurationExtractor {
        return CloudLockConfigurationExtractor()
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    fun proxyScheduledLockAopBeanPostProcessor(
        lockingExecutor: LockingExecutor,
        lockConfigurationExtractor: CloudLockConfigurationExtractor
    ): MethodProxyScheduledLockAdvisor {
        return MethodProxyScheduledLockAdvisor(lockingExecutor, lockConfigurationExtractor)
    }

}
