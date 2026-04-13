package com.komoju.demo.presentation.paymentMethod

import com.komoju.demo.domain.models.DomainPaymentMethod

data class PaymentMethodItem(
    val id: String,
    val name: String,
    val paymentMethod: DomainPaymentMethod
)

fun DomainPaymentMethod.toUIItem(): PaymentMethodItem {
    return PaymentMethodItem(
        id = name,
        name = getName(),
        paymentMethod = this
    )
}

fun DomainPaymentMethod.getName(): String {
    return when (this) {
        DomainPaymentMethod.PAYPAY -> "PayPay"
        DomainPaymentMethod.RAKUTEN_PAY -> "Rakuten Pay"
        DomainPaymentMethod.ALIPAY -> "Alipay"
        DomainPaymentMethod.LINEPAY -> "LINE Pay"
        DomainPaymentMethod.UNKNOWN -> "Unknown"
    }
}