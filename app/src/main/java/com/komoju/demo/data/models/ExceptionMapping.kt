package com.komoju.demo.data.models

import com.komoju.demo.domain.models.DomainException
import io.ktor.client.plugins.ResponseException

fun Throwable.toDomain(): DomainException = when (this) {
    is DomainException -> this
    is ResponseException -> when (response.status.value) {
        429 -> DomainException.TooManyRequests()
        401 -> DomainException.Unauthorized()
        else -> DomainException.Unknown(this)
    }
    else -> DomainException.Unknown(this)
}
