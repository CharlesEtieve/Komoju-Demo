package com.komoju.demo.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorPopup(viewModel: BaseViewModel) {
    val popupState = viewModel.popUpState.safeCollect() ?: return
    AlertDialog(
        onDismissRequest = {
            viewModel.popUpState.value = null
        },
        text = { Text(popupState.message) },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.popUpState.value = null
                },
                content = { Text(popupState.buttonText) }
            )
        }
    )
}