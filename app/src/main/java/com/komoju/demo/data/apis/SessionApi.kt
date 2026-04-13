package com.komoju.demo.data.apis

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionRequest(
    val amount: Long,
    val currency: String,
    val method: String
)

@Serializable
data class CreateSessionResponse(
    @SerialName("session_id") val sessionId: String,
    @SerialName("qr_data_base64") val qrDataBase64: String
)

@Serializable
data class SessionStatusResponse(
    val status: String
)

@Serializable
data class MockPayResponse(
    val message: String
)

class SessionApi(private val httpClient: HttpClient) {

    suspend fun createSession(request: CreateSessionRequest): CreateSessionResponse {
        return httpClient.post("/api/sessions") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun getSessionStatus(sessionId: String): SessionStatusResponse {
        return httpClient.get("/api/sessions/$sessionId/status").body()
    }

    suspend fun mockPay(sessionId: String): MockPayResponse {
        return httpClient.post("/api/sessions/$sessionId/pay").body()
    }
}