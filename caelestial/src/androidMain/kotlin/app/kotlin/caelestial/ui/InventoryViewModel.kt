package app.kotlin.caelestial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kotlin.caelestial.data.Item
import app.kotlin.caelestial.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class InventoryViewModel(itemsRepository: ItemsRepository) : ViewModel() {
    val inventoryUiState: StateFlow<InventoryUiState> =
        itemsRepository.getAllItemsStream().map { InventoryUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = InventoryUiState()
            )
}

data class InventoryUiState(val itemList: List<Item> = listOf())
