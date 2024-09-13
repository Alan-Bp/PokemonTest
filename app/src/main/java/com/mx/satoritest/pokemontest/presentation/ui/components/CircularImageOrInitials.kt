package com.mx.satoritest.pokemontest.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mx.satoritest.pokemontest.R

@Composable
fun CircularImage(
    imageUrl: String,
    initials: String,
    modifier: Modifier = Modifier,
    placeholder: Painter = painterResource(id = R.drawable.ic_placeholder),
    backgroundColor: Color = Color.Gray,
    textColor: Color = Color.White
) {
    val isImageUrlValid =
        imageUrl.isNotEmpty() && imageUrl.startsWith("http")
    val painter = rememberAsyncImagePainter(
        model = if (isImageUrlValid) imageUrl else null,
        placeholder = placeholder
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(60.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
    ) {
        if (isImageUrlValid) {
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
        words.isEmpty() || words.first().isEmpty() || !words.first().firstOrNull()
            ?.isLetter()!! -> "?"
        words.size == 1 -> words[0].firstOrNull()?.uppercase()
            ?: "?"
        words.size >= 2 -> "${words[0].firstOrNull()?.uppercase() ?: ""}${
            words[1].firstOrNull()?.uppercase() ?: ""
        }"
        else -> "?"
    }
}
