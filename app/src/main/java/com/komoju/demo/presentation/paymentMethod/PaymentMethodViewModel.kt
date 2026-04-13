package com.komoju.demo.presentation.paymentMethod

import androidx.lifecycle.viewModelScope
import com.komoju.demo.domain.models.DomainPaymentMethod
import com.komoju.demo.domain.repositories.PaymentMethodRepository
import com.komoju.demo.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PaymentMethodViewModel(
    private val paymentMethodRepository: PaymentMethodRepository
) : BaseViewModel() {

    val isLoading = MutableStateFlow(true)
    val paymentMethods = MutableStateFlow<List<PaymentMethodItem>>(emptyList())

    init {
        loadPaymentMethods()
    }

    private fun loadPaymentMethods() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                paymentMethods.value = paymentMethodRepository.getPaymentMethodList()
                    //TODO test filter unknown payment method with a usecase
                    .filter { it != DomainPaymentMethod.UNKNOWN }
                    .map { it.toUIItem() }
            } catch (exception: Exception) {
                handleException(
                    exception = exception,
                    retryCallback = { loadPaymentMethods() }
                )
            } finally {
                isLoading.value = false
            }
        }
    }
}