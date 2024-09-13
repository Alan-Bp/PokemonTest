package com.mx.satoritest.pokemontest.domain.repository

import com.mx.satoritest.pokemontest.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonList(
        offset: Int,
        limit: Int
    ): List<Pokemon>

    suspend fun getPokemonDetails(id: Int): Pokemon
    suspend fun isFavorite(id: Int): Boolean
    suspend fun addFavorite(id: Int)
    suspend fun removeFavorite(id: Int)
}