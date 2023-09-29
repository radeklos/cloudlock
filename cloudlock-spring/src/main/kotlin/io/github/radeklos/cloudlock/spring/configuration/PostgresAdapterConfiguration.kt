package io.github.radeklos.cloudlock.spring.configuration

import io.github.radeklos.cloudlock.adapter.Adapter
import io.github.radeklos.cloudlock.adapter.sql.JooqRepository
import io.github.radeklos.cloudlock.adapter.sql.PostgresAdapter
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Role
import javax.sql.DataSource

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConditionalOnClass(value = [DataSource::class])
class PostgresAdapterConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    fun jooqRepository(dataSource: DataSource): JooqRepository {
        return JooqRepository(dataSource)
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    fun adapter(repository: JooqRepository): Adapter {
        return PostgresAdapter(repository)
    }

}
