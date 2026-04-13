package com.komoju.demo.presentation.scan

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.komoju.demo.domain.models.DomainAmount
import com.komoju.demo.domain.models.DomainPaymentMethod
import com.komoju.demo.domain.models.DomainPaymentSession
import com.komoju.demo.domain.services.PaymentService
import com.komoju.demo.presentation.BaseViewModel
import com.komoju.demo.presentation.helpers.Base64ToBitmapHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class ScanViewModel(
    private val paymentService: PaymentService,
    private val base64ToBitmapHelper: Base64ToBitmapHelper
) : BaseViewModel() {

    val qrBitmap = MutableStateFlow<Bitmap?>(null)
    val isLoading = MutableStateFlow(true)

    fun setup(amount: DomainAmount, paymentMethod: DomainPaymentMethod) {
        createPaymentSession(amount, paymentMethod)
    }

    private fun createPaymentSession(amount: DomainAmount, paymentMethod: DomainPaymentMethod) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val paymentSession = paymentService.createPaymentSession(amount, paymentMethod)
                qrBitmap.value = base64ToBitmapHelper.invoke(paymentSession.qrDataBase64)
                watchPaymentStatus(paymentSession, amount, paymentMethod)
            } catch (exception: Exception) {
                handleException(
                    exception = exception,
                    retryCallback = { createPaymentSession(amount, paymentMethod) }
                )
            }
        }
    }

    private fun watchPaymentStatus(paymentSession: DomainPaymentSession, amount: DomainAmount, paymentMethod: DomainPaymentMethod) {
        paymentService
            .getPaymentStatus(paymentSession.sessionId)
            .catch { exception ->
                handleException(
                    exception = exception,
                    retryCallback = { createPaymentSession(amount, paymentMethod) }
                )
            }
            .launchIn(viewModelScope)
    }
}
