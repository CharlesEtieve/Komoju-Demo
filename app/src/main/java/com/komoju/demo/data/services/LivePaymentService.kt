package com.komoju.demo.data.services

import com.komoju.demo.BuildConfig
import com.komoju.demo.data.apis.CreateSessionRequest
import com.komoju.demo.data.apis.SessionApi
import com.komoju.demo.data.models.toApiString
import com.komoju.demo.data.models.toDomain
import com.komoju.demo.data.models.toDomainPaymentStatus
import com.komoju.demo.data.utils.withRetry
import com.komoju.demo.domain.models.DomainAmount
import com.komoju.demo.domain.models.DomainException
import com.komoju.demo.domain.models.DomainPaymentMethod
import com.komoju.demo.domain.models.DomainPaymentSession
import com.komoju.demo.domain.models.DomainPaymentStatus
import com.komoju.demo.domain.services.PaymentService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class LivePaymentService(
    private val sessionApi: SessionApi
): PaymentService {

    override suspend fun createPaymentSession(
        amount: DomainAmount,
        paymentMethod: DomainPaymentMethod
    ): DomainPaymentSession {
        try {
            val request = CreateSessionRequest(
                amount = amount.inCents,
                currency = amount.currencyCode,
                method = paymentMethod.toApiString()
            )
            val response = sessionApi.createSession(request)
            return DomainPaymentSession(
                sessionId = response.sessionId,
                qrDataBase64 = response.qrDataBase64
            )
        } catch (e: Exception) {
            throw e.toDomain()
        }
    }

    override fun getPaymentStatus(paymentSessionId: String): Flow<DomainPaymentStatus> = flow {
        if (BuildConfig.MOCK_PAY_ENABLED) {
            mockPay(paymentSessionId)
        }
        while (true) {
            var delayMs = INITIAL_POLLING_INTERVAL_MS
            val status = withRetry(
                shouldRetry = { it is DomainException.TooManyRequests },
                onUpdateDelay = { delayMs = it }
            ) { sessionApi.getSessionStatus(paymentSessionId).status.toDomainPaymentStatus() }
            emit(status)
            when (status) {
                DomainPaymentStatus.PAID,
                DomainPaymentStatus.EXPIRED,
                DomainPaymentStatus.FAILED -> break
                DomainPaymentStatus.PENDING,
                DomainPaymentStatus.UNKNOWN -> delay(delayMs)
            }
        }
    }

    private suspend fun mockPay(paymentSessionId: String) {
        coroutineScope {
            launch {
                delay(MOCK_PAY_DELAY_MS)
                sessionApi.mockPay(paymentSessionId)
            }
        }
    }

    companion object {
        private const val MOCK_PAY_DELAY_MS = 2500L
        private const val INITIAL_POLLING_INTERVAL_MS = 2000L
    }
}