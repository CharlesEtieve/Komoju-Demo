package com.komoju.demo.domain.models

data class DomainAmount(
    val inCents: Long,
    val currencyCode: String = YEN_CURRENCY_CODE
) {
    companion object {
        const val YEN_CURRENCY_CODE = "JPY"
    }
}