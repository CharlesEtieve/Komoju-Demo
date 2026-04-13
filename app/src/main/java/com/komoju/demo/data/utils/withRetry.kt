package com.komoju.demo.data.utils

import com.komoju.demo.data.models.toDomain
import com.komoju.demo.domain.models.DomainException
import kotlinx.coroutines.delay

suspend fun <T> withRetry(
    initialDelayMs: Long = 1_000L,
    maxDelayMs: Long = 18_000L,
    backoffFactor: Double = 2.0,
    //TODO should retry for every network transient error
    shouldRetry: (DomainException) -> Boolean = { it is DomainException.TooManyRequests },
    onUpdateDelay: ((Long) -> Unit)? = null,
    block: suspend () -> T
): T {
    var delayMs = initialDelayMs
    while (true) {
        try {
            return block()
        } catch (exception: Exception) {
            val exception = exception.toDomain()
            if (shouldRetry(exception)) {
                delay(delayMs)
                delayMs = (delayMs * backoffFactor).toLong().coerceAtMost(maxDelayMs)
                onUpdateDelay?.invoke(delayMs)
            } else {
                throw exception
            }
        }
    }
}