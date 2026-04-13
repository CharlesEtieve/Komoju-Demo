package com.komoju.demo.presentation.paymentCompletion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komoju.demo.domain.models.DomainPaymentMethod
import com.komoju.demo.presentation.paymentMethod.getName

@Composable
fun PaymentCompletionScreen(
    amount: Long,
    paymentMethod: DomainPaymentMethod,
    onGoBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Payment completed!", fontSize = 24.sp)

        Spacer(Modifier.height(16.dp))

        //TODO create AmountFormatterUseCase
        Text("Amount: ${amount}円", fontSize = 16.sp)
        Text("Payment Method: ${paymentMethod.getName()}", fontSize = 16.sp)

        Spacer(Modifier.height(24.dp))

        Button(onClick = onGoBack) {
            Text("Done")
        }
    }
}
