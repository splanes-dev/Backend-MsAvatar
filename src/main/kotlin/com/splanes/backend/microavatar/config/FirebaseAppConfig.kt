package com.splanes.mstest.infrastructure.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
class FirebaseAppConfig(
    @Value("\${infra.firebase.bucket}") private val bucketName: String,
    @Value("\${infra.firebase.config}") private val configFilename: String
) {
    init {
        val account = ClassPathResource(configFilename)
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(account.inputStream))
            .setStorageBucket(bucketName)
            .build()
        FirebaseApp.initializeApp(options)
    }
}