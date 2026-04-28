package app.kotlin.caelestial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.kotlin.caelestial.data.InventoryDatabase
import app.kotlin.caelestial.data.OfflineItemsRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Initialize database and repository
        val database = InventoryDatabase.getDatabase(applicationContext)
        val itemsRepository = OfflineItemsRepository(database.itemDao())

        setContent {
            InventoryApp(itemsRepository)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}