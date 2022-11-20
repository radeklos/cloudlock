package com.github.radeklos.cloudlock.spring.configuration

import com.github.radeklos.cloudlock.spring.aop.MethodProxyScheduledLockAdvisor
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Role

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
class MethodProxyLockConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    fun proxyScheduledLockAopBeanPostProcessor(): MethodProxyScheduledLockAdvisor {
        return MethodProxyScheduledLockAdvisor()
    }

}