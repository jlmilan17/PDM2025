package com.mercatto.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.mercatto.myapplication.data.model.Product

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.width(160.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}
