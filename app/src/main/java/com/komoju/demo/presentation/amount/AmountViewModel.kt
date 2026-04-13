package com.komoju.demo.presentation.amount

import com.komoju.demo.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AmountViewModel : BaseViewModel() {

    val amount = MutableStateFlow("")

    val isValid: Boolean
        get() = amount.value.toLongOrNull()?.let { it > 0 } == true

    fun onAmountChanged(value: String) {
        if (value.all { it.isDigit() }) {
            amount.value = value
        }
    }
}
