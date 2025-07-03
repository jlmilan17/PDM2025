package com.mercatto.myapplication.ui.sell

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mercatto.myapplication.data.model.Product
import com.mercatto.myapplication.ui.components.StoreImageSelector
import com.mercatto.myapplication.viewmodel.ProductViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellScreen(
    navController: NavController,
    viewModel: ProductViewModel
) {
    var productTitle by remember { mutableStateOf("") }
    var productPriceText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var productDescription by remember { mutableStateOf("") }
    var productImageUri by remember { mutableStateOf<Uri?>(null) }
    val categories by viewModel.categories.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val mainColor = Color(14, 70, 61)

    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "unknown-user"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Publicar artículo", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = mainColor),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StoreImageSelector(
                storeImageUri = productImageUri,
                onImageSelected = { productImageUri = it },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = productTitle,
                onValueChange = { productTitle = it },
                label = { Text("Título") },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = productPriceText,
                onValueChange = { productPriceText = it },
                label = { Text("Precio") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedCategory ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = productDescription,
                onValueChange = { productDescription = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                minLines = 3,
                maxLines = Int.MAX_VALUE
            )

            Button(
                onClick = {
                    val price = productPriceText.toDoubleOrNull() ?: 0.0
                    val productId = UUID.randomUUID().toString()

                    if (productImageUri != null) {
                        val storageRef = FirebaseStorage.getInstance()
                            .reference.child("product_images/$productId.jpg")

                        storageRef.putFile(productImageUri!!)
                            .addOnSuccessListener {
                                storageRef.downloadUrl.addOnSuccessListener { uri ->
                                    val newProduct = Product(
                                        id = productId,
                                        title = productTitle,
                                        price = price,
                                        description = productDescription,
                                        category = selectedCategory ?: "Sin categoría",
                                        image = uri.toString(), 
                                        ownerId = userId
                                    )

                                    db.collection("products")
                                        .document(newProduct.id)
                                        .set(newProduct)
                                        .addOnSuccessListener {
                                            viewModel.publishProduct(newProduct)
                                            navController.popBackStack()
                                        }
                                        .addOnFailureListener {
                                            println("Error al publicar el producto: ${it.message}")
                                        }
                                }
                            }
                            .addOnFailureListener {
                                println("Error al subir imagen: ${it.message}")
                            }

                    } else {
                        val newProduct = Product(
                            id = productId,
                            title = productTitle,
                            price = price,
                            description = productDescription,
                            category = selectedCategory ?: "Sin categoría",
                            image = "",
                            ownerId = userId
                        )

                        db.collection("products")
                            .document(newProduct.id)
                            .set(newProduct)
                            .addOnSuccessListener {
                                viewModel.publishProduct(newProduct)
                                navController.popBackStack()
                            }
                            .addOnFailureListener {
                                println("Error al publicar el producto: ${it.message}")
                            }
                    }
                }
                ,
                modifier = Modifier.fillMaxWidth(),
                colors   = ButtonDefaults.buttonColors(
                    containerColor = mainColor,
                    contentColor   = Color.White
                )
            ) {
                Text("Publicar producto")
            }
        }
    }
}
