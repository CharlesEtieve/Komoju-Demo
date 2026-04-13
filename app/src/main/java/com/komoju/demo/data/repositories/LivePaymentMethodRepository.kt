package com.komoju.demo.data.repositories

import com.komoju.demo.data.apis.PaymentMethodApi
import com.komoju.demo.data.models.toDomainPaymentMethod
import com.komoju.demo.domain.models.DomainPaymentMethod
import com.komoju.demo.domain.repositories.PaymentMethodRepository

class LivePaymentMethodRepository(
    private val paymentMethodApi: PaymentMethodApi
): PaymentMethodRepository {

    override suspend fun getPaymentMethodList(): List<DomainPaymentMethod> {
        val response = paymentMethodApi.getPaymentMethods()
        return response.paymentMethods
            .map { it.toDomainPaymentMethod() }
    }
}