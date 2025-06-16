package com.mercatto.myapplication.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import androidx.compose.material.icons.filled.CameraAlt

@Composable
fun StoreImageSelector(
    storeImageUri: Uri?,
    onImageSelected: (Uri?) -> Unit,
    modifier: Modifier = Modifier
) {
    // Launcher para escoger imagen de galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImageSelected(uri)
    }

    Surface(
        modifier = modifier
            .size(120.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.medium
            )
            .clickable { launcher.launch("image/*") },
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (storeImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(storeImageUri),
                    contentDescription = "Imagen de la tienda",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Seleccionar imagen de la tienda"
                )
            }
        }
    }
}
