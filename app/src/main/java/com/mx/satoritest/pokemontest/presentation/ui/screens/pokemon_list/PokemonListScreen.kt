package com.mx.satoritest.pokemontest.presentation.ui.screens.pokemon_list

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mx.satoritest.pokemontest.R
import com.mx.satoritest.pokemontest.domain.model.Pokemon
import com.mx.satoritest.pokemontest.domain.usescase.GetPokemonListUseCase
import com.mx.satoritest.pokemontest.presentation.ui.components.CircularImage
import com.mx.satoritest.pokemontest.presentation.ui.components.getInitials
import com.mx.satoritest.pokemontest.presentation.ui.theme.LightBlue
import com.mx.satoritest.pokemontest.presentation.ui.theme.RedLight
import com.mx.satoritest.pokemontest.presentation.viewmodel.MockPokemonRepository
import com.mx.satoritest.pokemontest.presentation.viewmodel.MockPreviewVM
import com.mx.satoritest.pokemontest.presentation.viewmodel.PokemonListViewModel

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    modifier: Modifier = Modifier,
    onPokemonClick: (Int) -> Unit
) {
    val pokemonList by viewModel.pokemonList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val lazyGridState = rememberLazyGridState()
    val backgroundColor = LightBlue
    val titleColor = Color.White
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column {
            Text(
                text = "PokeDex",
                color = titleColor,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RedLight),
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when {
                    isLoading && pokemonList.isEmpty() -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }

                    pokemonList.isNotEmpty() -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            state = lazyGridState,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(pokemonList) { pokemon ->
                                var isFavorite by remember { mutableStateOf(false) }
                                LaunchedEffect(pokemon.id) {
                                    isFavorite = viewModel.isFavorite(pokemon.id)
                                }
                                PokemonListItem(
                                    pokemon = pokemon,
                                    isFavorite = isFavorite,
                                    onClick = { pokemonId -> onPokemonClick(pokemonId) }
                                )
                            }
                        }
                        if (isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .align(Alignment.BottomCenter),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        LaunchedEffect(lazyGridState.firstVisibleItemIndex) {
                            val lastVisibleItemIndex =
                                lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                            Log.d(
                                "PokemonListScreen",
                                "Last visible item index: $lastVisibleItemIndex"
                            )
                            if (lastVisibleItemIndex != null) {
                                if (lastVisibleItemIndex >= pokemonList.size - 1 && !isLoading) {
                                    Log.d("PokemonListScreen", "Loading more Pokémon")
                                    viewModel.loadMorePokemons()
                                }
                            }
                        }
                    }

                    else -> {
                        Text(
                            text = "No Pokémon available",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonListItem(
    pokemon: Pokemon,
    isFavorite: Boolean,
    onClick: (Int) -> Unit
) {
    val isSmallScreen = LocalConfiguration.current.screenWidthDp < 360
    val imageModifier = Modifier
        .size(if (isSmallScreen) 64.dp else 90.dp)
        .clip(CircleShape)
        .border(2.dp, Color.Black, CircleShape)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick(pokemon.id) }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = imageModifier
        ) {
            Image(
                painter = painterResource(id = R.drawable.pokeball_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.5f),
                contentScale = ContentScale.Crop
            )
            CircularImage(
                imageUrl = pokemon.imageUrl,
                initials = getInitials(pokemon.name),
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
    if (isFavorite) {
        Icon(
            painter = painterResource(id = R.drawable.ic_icon),
            contentDescription = "Favorite",
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPokemonListScreen() {
    PokemonListScreen(
        viewModel = MockPreviewVM(GetPokemonListUseCase(MockPokemonRepository())),
        onPokemonClick = {}
    )
}