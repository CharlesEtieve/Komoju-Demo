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