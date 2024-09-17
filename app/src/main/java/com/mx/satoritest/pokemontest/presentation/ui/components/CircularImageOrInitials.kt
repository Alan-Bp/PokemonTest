package com.mx.satoritest.pokemontest.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mx.satoritest.pokemontest.R
import com.mx.satoritest.pokemontest.presentation.ui.theme.RedLight

@Composable
fun CircularImage(
    imageUrl: String,
    initials: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = RedLight,
    textColor: Color = Color.White
) {
    val isImageUrlValid = imageUrl.isNotEmpty() && imageUrl.startsWith("http")
    var hasError by remember { mutableStateOf(false) }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(if (isImageUrlValid) imageUrl else null)
            .crossfade(true)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error_placeholder)
            .build(),
        onError = {
            hasError = true
        }
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(60.dp)
            .clip(CircleShape)
    ) {
        if (!hasError && isImageUrlValid) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        } else {
            val displayInitials = getInitials(initials)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(backgroundColor, shape = CircleShape)
                    .fillMaxSize()
            ) {
                Text(
                    text = displayInitials,
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

fun getInitials(name: String): String {
    val words = name.split(" ")
    return when {
        name.isBlank() -> "?"
        words.size == 1 -> words[0].take(2).uppercase()
        words.size >= 2 -> "${words[0].firstOrNull()?.uppercase() ?: ""}${
            words[1].firstOrNull()?.uppercase() ?: ""
        }"

        else -> "?"
    }
}
