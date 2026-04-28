package app.kotlin.caelestial.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.kotlin.caelestial.data.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    onNavigateToItemEntry: () -> Unit,
    viewModel: InventoryViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.inventoryUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Inventory") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item"
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        InventoryBody(
            itemList = uiState.itemList,
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        )
    }
}

@Composable
private fun InventoryBody(
    itemList: List<Item>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (itemList.isEmpty()) {
            Text(
                text = "No items in inventory",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            InventoryList(
                itemList = itemList,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
private fun InventoryList(
    itemList: List<Item>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = itemList, key = { it.id }) { item ->
            InventoryItem(
                item = item,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun InventoryItem(
    item: Item,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "$${item.price}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = "${item.quantity} in stock",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
