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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mercatto.myapplication.ui.components.ProductCard
import com.mercatto.myapplication.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPostsScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {
    val myProducts by viewModel.publishedProducts.collectAsState()

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
        if (myProducts.isEmpty()) {
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
                items(myProducts) { product ->
                    ProductCard(
                        product = product,
                        onClick = {
                            navController.navigate("detail/${product.id}")
                        },
                        onToggleFavorite = null
                    )
                }
            }
        }
    }
}
