package com.komoju.demo.data.models

import com.komoju.demo.domain.models.DomainPaymentStatus

fun String.toDomainPaymentStatus(): DomainPaymentStatus {
    return when (this) {
        "PENDING" -> DomainPaymentStatus.PENDING
        "PAID" -> DomainPaymentStatus.PAID
        "EXPIRED" -> DomainPaymentStatus.EXPIRED
        "FAILED" -> DomainPaymentStatus.FAILED
        else -> DomainPaymentStatus.UNKNOWN
    }
}