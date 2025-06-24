package com.mercatto.myapplication.ui.detailProduct

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.mercatto.myapplication.viewmodel.ProductViewModel

@Composable
fun DetailScreen(
    entry: NavBackStackEntry,
    viewModel: ProductViewModel
) {

    val productId = entry.arguments?.getString("id") ?: return

    val product = viewModel.filteredProducts.value
        .firstOrNull { it.id == productId }
        ?: return

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(product.title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text("$${product.price}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(22.dp))
            Text(product.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
