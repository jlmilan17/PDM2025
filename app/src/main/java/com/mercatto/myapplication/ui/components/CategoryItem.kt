package com.mercatto.myapplication.ui.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryItem(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val mainColor = Color(14, 70, 61)

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = if (selected)
                Color.White
            else
                mainColor,
            modifier = Modifier
                .size(72.dp)
                .clickable(onClick = onClick)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector   = Icons.Filled.Star,
                    contentDescription = name,
                    tint = if (selected)
                        mainColor
                    else
                        Color.White.copy(alpha = 0.5f),
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(text = name, style = MaterialTheme.typography.bodySmall)
    }

}
