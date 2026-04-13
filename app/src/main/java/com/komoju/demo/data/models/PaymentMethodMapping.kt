package com.komoju.demo.data.models

import com.komoju.demo.domain.models.DomainPaymentMethod

fun String.toDomainPaymentMethod(): DomainPaymentMethod {
    return when (this) {
        "paypay" -> DomainPaymentMethod.PAYPAY
        "rakuten_pay" -> DomainPaymentMethod.RAKUTEN_PAY
        "alipay" -> DomainPaymentMethod.ALIPAY
        "linepay" -> DomainPaymentMethod.LINEPAY
        else -> DomainPaymentMethod.UNKNOWN
    }
}

fun DomainPaymentMethod.toApiString(): String {
    return when (this) {
        DomainPaymentMethod.PAYPAY -> "paypay"
        DomainPaymentMethod.RAKUTEN_PAY -> "rakuten_pay"
        DomainPaymentMethod.ALIPAY -> "alipay"
        DomainPaymentMethod.LINEPAY -> "linepay"
        DomainPaymentMethod.UNKNOWN -> "unknown"
    }
}