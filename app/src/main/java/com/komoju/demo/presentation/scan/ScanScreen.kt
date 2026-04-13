package com.komoju.demo.presentation.scan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komoju.demo.domain.models.DomainAmount
import com.komoju.demo.domain.models.DomainPaymentMethod
import com.komoju.demo.domain.models.DomainPaymentStatus
import com.komoju.demo.presentation.ErrorPopup
import com.komoju.demo.presentation.safeCollect

@Composable
fun ScanScreen(
    amount: DomainAmount,
    paymentMethod: DomainPaymentMethod,
    viewModel: ScanViewModel,
    onPaymentCompleted: () -> Unit = {},
    onPaymentFailed: () -> Unit = {}
) {
    ErrorPopup(viewModel)

    LaunchedEffect(amount, paymentMethod) {
        viewModel.setup(amount, paymentMethod)
    }

    LaunchedEffect(Unit) {
        viewModel.paymentResult.collect { status ->
            when (status) {
                DomainPaymentStatus.PAID -> onPaymentCompleted()
                DomainPaymentStatus.FAILED,
                DomainPaymentStatus.EXPIRED -> onPaymentFailed()
                else -> { }
            }
        }
    }

    val qrBitmap = viewModel.qrBitmap.safeCollect()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        qrBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "QR Code",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(250.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Please scan this QR Code to pay",
            fontSize = 16.sp
        )
    }
}
