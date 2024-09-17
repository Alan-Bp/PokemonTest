package com.mx.satoritest.pokemontest.data.repository

import android.util.Log
import com.mx.satoritest.pokemontest.data.local.FavoritePokemonEntity
import com.mx.satoritest.pokemontest.data.local.PokemonDao
import com.mx.satoritest.pokemontest.data.remote.PokeApiService
import com.mx.satoritest.pokemontest.data.remote.mapToDomain
import com.mx.satoritest.pokemontest.data.remote.toPokemon
import com.mx.satoritest.pokemontest.domain.model.Pokemon
import com.mx.satoritest.pokemontest.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val apiService: PokeApiService,
    private val pokemonDao: PokemonDao
) : PokemonRepository {

    override suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon> {
        return try {
            val response = apiService.getPokemonList(offset, limit)
            val pokemonResults = response.results
            val pokemons = pokemonResults.map { result ->
                val id = extractIdFromUrl(result.url)
                val details = apiService.getPokemonDetails(id)
                val types = details.types.map { it.type.name }
                mapToDomain(
                    pokemonDetailResponse = details,
                    height = details.height.toDouble(),
                    weight = details.weight.toDouble(),
                    types = types
                )
            }
            Log.d("PokemonRepositoryImpl", "Pokemons fetched: $pokemons")
            pokemonDao.insertPokemons(pokemons.map { it.toEntity() })
            pokemons
        } catch (e: Exception) {
            Log.e("PokemonRepositoryImpl", "Error fetching Pokémon list", e)
            val cachedPokemons = pokemonDao.getAllPokemons().map { it.toPokemon() }
            Log.d("PokemonRepositoryImpl", "Pokemons from cache: $cachedPokemons")
            cachedPokemons
        }
    }

    override suspend fun getPokemonDetails(id: Int): Flow<Pokemon> = flow {
        try {
            val response = apiService.getPokemonDetails(id)
            val pokemon = response.toPokemon()
            Log.d("PokemonRepositoryImpl", "Pokemon fetched: $pokemon")
            pokemonDao.insertPokemons(listOf(pokemon.toEntity()))
            emit(pokemon)
        } catch (e: Exception) {
            Log.e("PokemonRepositoryImpl", "Error fetching Pokémon details", e)
            val cachedPokemon = pokemonDao.getPokemonById(id)?.toPokemon()
                ?: throw Exception("Pokemon not found")
            Log.d("PokemonRepositoryImpl", "Pokemon from cache: $cachedPokemon")
            emit(cachedPokemon)
        }
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return try {
            val result = pokemonDao.isFavorite(id)
            result
        } catch (e: Exception) {
            Log.e("PokemonRepositoryImpl", "Error checking if Pokémon is favorite", e)
            false
        }
    }

    override suspend fun addFavorite(id: Int) {
        try {
            val favoriteEntity = FavoritePokemonEntity(id)
            pokemonDao.addFavorite(favoriteEntity)
        } catch (e: Exception) {
            Log.e("PokemonRepositoryImpl", "Error adding Pokémon to favorites", e)
            throw Exception("Error adding Pokémon to favorites", e)
        }
    }

    override suspend fun removeFavorite(id: Int) {
        try {
            pokemonDao.removeFavorite(id)
        } catch (e: Exception) {
            Log.e("PokemonRepositoryImpl", "Error removing Pokémon from favorites", e)
            throw Exception("Error removing Pokémon from favorites", e)
        }
    }

    private fun extractIdFromUrl(url: String): Int {
        return try {
            if (url.isEmpty()) {
                throw IllegalArgumentException("La URL está vacía.")
            }
            val idString = url.trimEnd('/').substringAfterLast('/').trim()
            if (idString.isNotEmpty() && idString.all { it.isDigit() }) {
                idString.toInt()
            } else {
                throw IllegalArgumentException("La URL no contiene un ID numérico válido: $url")
            }
        } catch (e: NumberFormatException) {
            Log.e("PokemonRepositoryImpl", "Error al convertir el ID de la URL: $url", e)
            throw IllegalArgumentException("ID extraído no es un número válido: $url", e)
        } catch (e: IllegalArgumentException) {
            Log.e("PokemonRepositoryImpl", "Error en la URL proporcionada: $url", e)
            throw e
        }
    }
}
