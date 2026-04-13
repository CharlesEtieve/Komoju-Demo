package com.komoju.demo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.komoju.demo.domain.models.DomainAmount
import com.komoju.demo.domain.models.DomainPaymentMethod
import com.komoju.demo.presentation.amount.AmountScreen
import com.komoju.demo.presentation.amount.AmountViewModel
import com.komoju.demo.presentation.paymentCompletion.PaymentCompletionScreen
import com.komoju.demo.presentation.paymentFailed.PaymentFailedScreen
import com.komoju.demo.presentation.paymentMethod.PaymentMethodScreen
import com.komoju.demo.presentation.paymentMethod.PaymentMethodViewModel
import com.komoju.demo.presentation.scan.ScanScreen
import com.komoju.demo.presentation.scan.ScanViewModel
import com.komoju.demo.ui.theme.DemoTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object PaymentMethodRoute

@Serializable
data class AmountRoute(val paymentMethod: DomainPaymentMethod)

@Serializable
data class ScanRoute(val paymentMethod: DomainPaymentMethod, val amount: Long)

@Serializable
data class PaymentCompletionRoute(val paymentMethod: DomainPaymentMethod, val amount: Long)

@Serializable
object PaymentFailedRoute

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                val navController = rememberNavController()
                val currentEntry by navController.currentBackStackEntryAsState()
                val canGoBack = currentEntry != null && navController.previousBackStackEntry != null

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Komoju Demo") },
                            navigationIcon = {
                                if (canGoBack) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = PaymentMethodRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<PaymentMethodRoute> {
                            PaymentMethodScreen(
                                viewModel = koinViewModel<PaymentMethodViewModel>(),
                                onPaymentMethodSelected = { paymentMethod ->
                                    navController.navigate(AmountRoute(paymentMethod))
                                }
                            )
                        }

                        composable<AmountRoute> { backStackEntry ->
                            val route = backStackEntry.toRoute<AmountRoute>()
                            AmountScreen(
                                viewModel = koinViewModel<AmountViewModel>(),
                                onGeneratePaymentLink = { amount ->
                                    navController.navigate(ScanRoute(route.paymentMethod, amount))
                                }
                            )
                        }

                        composable<ScanRoute> { backStackEntry ->
                            val route = backStackEntry.toRoute<ScanRoute>()
                            ScanScreen(
                                amount = DomainAmount(inCents = route.amount),
                                paymentMethod = route.paymentMethod,
                                viewModel = koinViewModel<ScanViewModel>(),
                                onPaymentCompleted = {
                                    navController.navigate(
                                        PaymentCompletionRoute(route.paymentMethod, route.amount)
                                    ) {
                                        popUpTo(PaymentMethodRoute) { inclusive = false }
                                    }
                                },
                                onPaymentFailed = {
                                    navController.navigate(PaymentFailedRoute) {
                                        popUpTo(PaymentMethodRoute) { inclusive = false }
                                    }
                                }
                            )
                        }

                        composable<PaymentCompletionRoute> { backStackEntry ->
                            val route = backStackEntry.toRoute<PaymentCompletionRoute>()
                            PaymentCompletionScreen(
                                amount = route.amount,
                                paymentMethod = route.paymentMethod,
                                onGoBack = {
                                    navController.popBackStack(
                                        PaymentMethodRoute,
                                        inclusive = false
                                    )
                                }
                            )
                        }

                        composable<PaymentFailedRoute> {
                            PaymentFailedScreen(
                                onGoBack = {
                                    navController.popBackStack(
                                        PaymentMethodRoute,
                                        inclusive = false
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
