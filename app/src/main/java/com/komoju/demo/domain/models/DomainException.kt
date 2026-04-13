package com.komoju.demo.domain.models

sealed class DomainException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    class TooManyRequests : DomainException()
    class Unauthorized : DomainException()
    class Unknown(cause: Throwable) : DomainException()
}