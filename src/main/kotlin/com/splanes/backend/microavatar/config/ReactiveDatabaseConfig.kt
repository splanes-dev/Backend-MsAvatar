package com.splanes.mstest.infrastructure.config

import com.splanes.mstest.infrastructure.repository.db.AvatarDatabaseRepository
import io.r2dbc.spi.ConnectionFactory
import org.mariadb.r2dbc.MariadbConnectionConfiguration
import org.mariadb.r2dbc.MariadbConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories



@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@EnableR2dbcRepositories(basePackageClasses = [AvatarDatabaseRepository::class])
class ReactiveDatabaseConfig(
    @Value("\${infra.db.connection.host}") private val host: String,
    @Value("\${infra.db.connection.port}") private val port: String,
    @Value("\${infra.db.access.user}") private val user: String,
    @Value("\${infra.db.access.secret}") private val secret: String,
    @Value("\${infra.db.name}") private val db: String,
) : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory = MariadbConnectionConfiguration.builder()
        .host(host).port(port.toInt())
        .username(user).password(secret)
        .database(db)
        .build()
        .run(MariadbConnectionFactory::from)
}