package com.mx.satoritest.pokemontest.presentation.ui.screens.pokemon_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.mx.satoritest.pokemontest.presentation.viewmodel.PokemonDetailViewModel
import com.mx.satoritest.pokemontest.presentation.viewmodel.ViewModelFactory
import com.mx.satoritest.pokemontest.data.repository.AppContainer

@Composable
fun PokemonDetailScreen(
    pokemonId: Int
) {
    val pokemonRepository = AppContainer.getPokemonRepository()
    val viewModel: PokemonDetailViewModel = viewModel(
        factory = ViewModelFactory(pokemonRepository)
    )

    val pokemon by viewModel.pokemonDetail.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    LaunchedEffect(pokemonId) {
        viewModel.loadPokemonDetail(pokemonId)
        viewModel.checkIfFavorite(pokemonId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState())
        ) {
            pokemon?.let { pokemonDetail ->
                Image(
                    painter = rememberImagePainter(pokemonDetail.imageUrl),
                    contentDescription = pokemonDetail.name,
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                )

                Text(
                    text = "Name: ${pokemonDetail.name}",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Height: ${pokemonDetail.height}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Weight: ${pokemonDetail.weight}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Button(
                    onClick = { viewModel.toggleFavorite(pokemonId) },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                ) {
                    Text(if (isFavorite) "Remove from Favorites" else "Add to Favorites")
                }
            } ?: run {
                Text(
                    text = "Loading...",
                )
            }
        }
    }
}
