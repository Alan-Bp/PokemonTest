package com.mx.satoritest.pokemontest.data.repository

import android.content.Context
import com.mx.satoritest.pokemontest.data.local.PokemonDatabase
import com.mx.satoritest.pokemontest.data.remote.PokeApiService
import com.mx.satoritest.pokemontest.domain.repository.PokemonRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppContainer {
    private var pokemonRepository: PokemonRepository? = null
    fun initialize(context: Context) {
        if (pokemonRepository == null) {
            val database = PokemonDatabase.getDatabase(context)
            val apiService = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokeApiService::class.java)
            pokemonRepository = PokemonRepositoryImpl(apiService, database.pokemonDao())
        }
    }

    fun getPokemonRepository(): PokemonRepository {
        return pokemonRepository ?: throw IllegalStateException("AppContainer not initialized")
    }
}