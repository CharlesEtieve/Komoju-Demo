package com.komoju.demo.presentation

import androidx.lifecycle.ViewModel
import com.komoju.demo.data.models.toDomain
import com.komoju.demo.domain.models.DomainException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

open class BaseViewModel: ViewModel() {

    val popUpState: MutableStateFlow<ErrorPopupState?> = MutableStateFlow(null)

    protected fun handleException(exception: Throwable, retryCallback: () -> Unit) {
        when (exception.toDomain()) {
            is DomainException.TooManyRequests -> {
                showRetryMessage(retryCallback)
            }
            is DomainException.Unauthorized -> {
                // TODO logout
                showRetryMessage(retryCallback)
            }
            is DomainException.Unknown -> {
                //TODO log exception into our error tracker (Sentry, Crashlytics etc..)
                showRetryMessage(retryCallback)
            }
        }
    }

    fun showRetryMessage(
        callback: () -> Unit = {}
    ) {
        popUpState.update {
            ErrorPopupState(
                message = "Error occurred",
                buttonText = "Retry",
                callback = {
                    popUpState.value = null
                    callback.invoke()
                }
            )
        }
    }

    data class ErrorPopupState(
        val message: String,
        val buttonText: String,
        val callback: () -> Unit = {}
    )
}