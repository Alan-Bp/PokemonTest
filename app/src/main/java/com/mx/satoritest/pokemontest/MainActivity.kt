package com.mx.satoritest.pokemontest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mx.satoritest.pokemontest.presentation.ui.screens.pokemon_detail.PokemonDetailScreen
import com.mx.satoritest.pokemontest.presentation.ui.screens.pokemon_list.PokemonListScreen
import com.mx.satoritest.pokemontest.presentation.ui.theme.PokemonAppTheme
import com.mx.satoritest.pokemontest.presentation.viewmodel.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val pokemonListViewModel: PokemonListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokemonAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemonList") {
                    composable("pokemonList") {
                        PokemonListScreen(
                            viewModel = pokemonListViewModel,
                            onPokemonClick = { pokemonId ->
                                navController.navigate("pokemonDetail/$pokemonId")
                            }
                        )
                    }
                    composable("pokemonDetail/{pokemonId}") { backStackEntry ->
                        val pokemonId = backStackEntry.arguments?.getString("pokemonId")?.toInt()
                            ?: return@composable
                        PokemonDetailScreen(
                            pokemonId = pokemonId
                        )
                    }
                }
            }
        }
    }
}
