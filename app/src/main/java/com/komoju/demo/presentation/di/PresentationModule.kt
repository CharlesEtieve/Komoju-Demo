package com.komoju.demo.presentation.di

import com.komoju.demo.presentation.amount.AmountViewModel
import com.komoju.demo.presentation.paymentMethod.PaymentMethodViewModel
import com.komoju.demo.presentation.scan.ScanViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val presentationModule = module {
    viewModel { PaymentMethodViewModel(get()) }
    viewModel { AmountViewModel() }
    viewModel { ScanViewModel(get()) }
}
