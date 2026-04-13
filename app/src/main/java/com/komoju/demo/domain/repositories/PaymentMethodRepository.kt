package com.komoju.demo.domain.repositories

import com.komoju.demo.domain.models.DomainPaymentMethod

interface PaymentMethodRepository {
    suspend fun getPaymentMethodList(): List<DomainPaymentMethod>
}