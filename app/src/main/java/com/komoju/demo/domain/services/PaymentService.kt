package com.komoju.demo.domain.services

import com.komoju.demo.domain.models.DomainAmount
import com.komoju.demo.domain.models.DomainPaymentMethod
import com.komoju.demo.domain.models.DomainPaymentSession
import com.komoju.demo.domain.models.DomainPaymentStatus
import kotlinx.coroutines.flow.Flow

interface PaymentService {
    suspend fun createPaymentSession(amount: DomainAmount, paymentMethod: DomainPaymentMethod): DomainPaymentSession
    fun getPaymentStatus(paymentSessionId: String): Flow<DomainPaymentStatus>
}