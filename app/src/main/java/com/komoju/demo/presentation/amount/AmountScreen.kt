package com.komoju.demo.presentation.amount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.komoju.demo.presentation.ErrorPopup
import com.komoju.demo.presentation.safeCollect

@Composable
fun AmountScreen(
    viewModel: AmountViewModel,
    onGeneratePaymentLink: (Long) -> Unit = {}
) {
    val amount = viewModel.amount.safeCollect()

    ErrorPopup(viewModel)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = amount,
            onValueChange = viewModel::onAmountChanged,
            label = { Text("Amount") },
            suffix = { Text("¥") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { amount.toLongOrNull()?.let(onGeneratePaymentLink) },
            enabled = viewModel.isValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate Payment Link")
        }
    }
}