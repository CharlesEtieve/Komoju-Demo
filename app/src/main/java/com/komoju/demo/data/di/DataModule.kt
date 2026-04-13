package com.komoju.demo.data.di

import com.komoju.demo.BuildConfig
import com.komoju.demo.data.apis.PaymentMethodApi
import com.komoju.demo.data.apis.SessionApi
import com.komoju.demo.data.repositories.LivePaymentMethodRepository
import com.komoju.demo.data.services.LivePaymentService
import com.komoju.demo.domain.repositories.PaymentMethodRepository
import com.komoju.demo.domain.services.PaymentService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient(OkHttp) {
            HttpResponseValidator {
                validateResponse { response ->
                    if (response.status.value >= 300) {
                        throw ResponseException(response, "HTTP ${response.status.value}")
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            defaultRequest {
                url(BuildConfig.BASE_URL)
                headers { append("Authorization", "Bearer ${BuildConfig.API_KEY}") }
            }
        }
    }

    factory { PaymentMethodApi(get()) }
    factory { SessionApi(get()) }

    factory<PaymentMethodRepository> { LivePaymentMethodRepository(get()) }
    factory<PaymentService> { LivePaymentService(get()) }
}
