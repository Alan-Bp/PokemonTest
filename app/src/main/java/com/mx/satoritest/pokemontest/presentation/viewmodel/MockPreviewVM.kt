package com.mx.satoritest.pokemontest.presentation.viewmodel

import com.mx.satoritest.pokemontest.domain.model.Pokemon
import com.mx.satoritest.pokemontest.domain.repository.PokemonRepository
import com.mx.satoritest.pokemontest.domain.usescase.GetPokemonListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

private val samplePokemons = listOf(
    Pokemon(
        id = 1,
        name = "Bulbasaur",
        imageUrl = "https://example.com/bulbasaur.png",
        height = 7.3,
        weight = 6.9,
        types = listOf("Grass", "Poison")
    )
)

class MockPreviewVM(
    getPokemonListUseCase: GetPokemonListUseCase
) : PokemonListViewModel(getPokemonListUseCase, MockPokemonRepository()) {
}

class MockPokemonRepository : PokemonRepository {

    override suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon> = samplePokemons
    override suspend fun getPokemonDetails(id: Int): Flow<Pokemon> {
        val pokemon = samplePokemons.find { it.id == id }
        return flowOf(
            pokemon ?: samplePokemons.first()
        )
    }

    override suspend fun addFavorite(id: Int) {}
    override suspend fun removeFavorite(id: Int) {}
    override suspend fun isFavorite(id: Int): Boolean = false
}
