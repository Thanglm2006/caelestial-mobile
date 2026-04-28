package app.kotlin.caelestial

import androidx.compose.runtime.*
import app.kotlin.caelestial.data.ItemsRepository
import app.kotlin.caelestial.ui.InventoryScreen
import app.kotlin.caelestial.ui.InventoryViewModel
import app.kotlin.caelestial.ui.ItemEntryScreen
import app.kotlin.caelestial.ui.ItemEntryViewModel

sealed class Screen {
    object Inventory : Screen()
    object Entry : Screen()
}

@Composable
fun InventoryApp(itemsRepository: ItemsRepository) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Inventory) }

    when (currentScreen) {
        is Screen.Inventory -> {
            val viewModel = remember { InventoryViewModel(itemsRepository) }
            InventoryScreen(
                onNavigateToItemEntry = { currentScreen = Screen.Entry },
                viewModel = viewModel
            )
        }
        is Screen.Entry -> {
            val viewModel = remember { ItemEntryViewModel(itemsRepository) }
            ItemEntryScreen(
                navigateBack = { currentScreen = Screen.Inventory },
                onNavigateUp = { currentScreen = Screen.Inventory },
                viewModel = viewModel
            )
        }
    }
}
