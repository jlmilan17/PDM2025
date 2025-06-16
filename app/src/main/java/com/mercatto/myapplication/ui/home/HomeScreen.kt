package com.mercatto.myapplication.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mercatto.myapplication.navigation.Destinations
import com.mercatto.myapplication.ui.components.*
import com.mercatto.myapplication.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProductViewModel
) {
    val categories       by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery      by viewModel.searchQuery.collectAsState()
    val products         by viewModel.filteredProducts.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute  = Destinations.HOME
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Spacer(Modifier.height(8.dp))
            SearchBar(query = searchQuery, onQueryChange = viewModel::onSearchQueryChange)
            Spacer(Modifier.height(12.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    CategoryItem(
                        name = "Todas",
                        selected = selectedCategory == null,
                        onClick = { viewModel.selectCategory(null) }
                    )
                }
                items(categories) { cat ->
                    CategoryItem(
                        name = cat.capitalize(),
                        selected = cat == selectedCategory,
                        onClick = { viewModel.selectCategory(cat) }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement   = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
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
