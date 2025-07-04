package com.mercatto.myapplication.ui.detailProduct

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.mercatto.myapplication.navigation.Destinations
import com.mercatto.myapplication.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    productId: String,
    viewModel: ProductViewModel = viewModel()
) {
    val product = viewModel.allProducts.collectAsState().value.find { it.id == productId }
    val currentUser = FirebaseAuth.getInstance().currentUser?.uid
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val mainColor = Color(14, 70, 61)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del producto", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = mainColor),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        product?.let { selectedProduct ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = selectedProduct.image,
                    contentDescription = selectedProduct.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Fit
                )

                Text(selectedProduct.title, style = MaterialTheme.typography.headlineSmall, color = mainColor)
                Text("$${selectedProduct.price}", style = MaterialTheme.typography.titleMedium)

                AssistChip(
                    onClick = {},
                    label = { Text(selectedProduct.category) },
                    enabled = true,
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = mainColor,
                        labelColor = Color.White
                    )
                )

                Text("Descripción", style = MaterialTheme.typography.titleSmall, color = mainColor)
                Text(selectedProduct.description, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.weight(1f))

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =  Arrangement.Center
                ) {
                    if (selectedProduct.ownerId == currentUser) {
                        Button(
                            onClick = { showDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Eliminar producto")
                        }
                    }

                    if (selectedProduct.ownerId != currentUser) {
                        Button(
                            onClick = {
                                navController.navigate(Destinations.MESSAGES)
                            },
                            colors   = ButtonDefaults.buttonColors(
                                containerColor = mainColor,
                                contentColor   = Color.White
                            )
                        ) {
                            Text("Contactar al vendedor")
                        }
                    }

                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("¿Eliminar producto?") },
                        text = { Text("¿Estás seguro de que deseas eliminar este producto? Esta acción no se puede deshacer.") },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    viewModel.deleteProduct(selectedProduct.id)
                                    viewModel.refreshPublishedProducts()
                                    Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
                                    showDialog = false
                                    navController.popBackStack()
                                }

                            ) {
                                Text("Eliminar", color = MaterialTheme.colorScheme.error)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
            }
        } ?: Box(
            Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Producto no encontrado")
        }
    }
}
