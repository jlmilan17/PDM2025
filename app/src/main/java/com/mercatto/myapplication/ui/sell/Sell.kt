package com.mercatto.myapplication.ui.sell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.mercatto.myapplication.viewmodel.ProductViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mercatto.myapplication.data.model.Product


@Composable
fun SellScreen(
    navController: NavController,
    viewModel: ProductViewModel
) {
    var productTitle by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf<Double>(0.0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(64.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Publicar artículo", modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 32.sp
                )
            }

            OutlinedTextField(
                value = productTitle,
                onValueChange = { productTitle = it },
                placeholder = { "Título" },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            )

//            OutlinedTextField(
//                value = productPrice,
//                onValueChange = { productPrice = it },
//                placeholder = { "Precio" },
//                shape = RoundedCornerShape(24.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(52.dp)
//            )



        }
    }
}

//@Composable
//fun PostProduct() {
//
//}