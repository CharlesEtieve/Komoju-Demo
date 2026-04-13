package com.komoju.demo.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.safeCollect(): T {
    return collectAsStateWithLifecycle().value
}
