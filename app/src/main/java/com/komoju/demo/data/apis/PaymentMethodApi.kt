package com.komoju.demo.data.apis

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentMethodListResponse(
    @SerialName("payment_methods") val paymentMethods: List<String>
)

class PaymentMethodApi(private val httpClient: HttpClient) {

    suspend fun getPaymentMethods(): PaymentMethodListResponse {
        return httpClient.get("/api/payment-methods").body()
    }
}
