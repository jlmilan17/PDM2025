package com.mercatto.myapplication.ui.mypostscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mercatto.myapplication.data.model.Product
import com.mercatto.myapplication.ui.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPostsScreen(navController: NavController) {
    // To Do: replace with actual data
    val dummyProducts = listOf(
        Product(
            id = "1",
            title = "Producto 1",
            price = 10.0,
            description = "Descripción de producto 1",
            category = "categoria1",
            image = "https://via.placeholder.com/150"
        ),
        Product(
            id = "2",
            title = "Producto 2",
            price = 20.0,
            description = "Descripción de producto 2",
            category = "categoria2",
            image = "https://via.placeholder.com/150"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis publicaciones") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        if (dummyProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Aún no has publicado ningún producto.")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(dummyProducts) { product ->
                    ProductCard(
                        product = product,
                        onClick = {
                            navController.navigate("detail/${product.id}")
                        }
                    )
                }
            }
        }
    }
}
