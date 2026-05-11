package com.example.cupcake

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.cupcake.model.OrderViewModel
import com.example.cupcake.ui.theme.*

enum class Screen {
    Start, Flavor, Pickup, Summary
}

@Composable
fun CupcakeApp(orderViewModel: OrderViewModel = viewModel()) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Start.name
    ) {

        composable(Screen.Start.name) {
            StartScreen(
                onNext = {
                    orderViewModel.setQuantity(it)
                    navController.navigate(Screen.Flavor.name)
                }
            )
        }

        composable(Screen.Flavor.name) {
            FlavorScreen(
                onNext = {
                    orderViewModel.setFlavor(it)
                    navController.navigate(Screen.Pickup.name)
                },
                onCancel = {
                    orderViewModel.reset()
                    navController.popBackStack(Screen.Start.name, false)
                }
            )
        }

        composable(Screen.Pickup.name) {
            PickupScreen(
                onNext = {
                    orderViewModel.setDate(it)
                    navController.navigate(Screen.Summary.name)
                },
                onCancel = {
                    orderViewModel.reset()
                    navController.popBackStack(Screen.Start.name, false)
                }
            )
        }

        composable(Screen.Summary.name) {
            SummaryScreen(
                orderViewModel = orderViewModel,
                onCancel = {
                    orderViewModel.reset()
                    navController.popBackStack(Screen.Start.name, false)
                }
            )
        }
    }
}