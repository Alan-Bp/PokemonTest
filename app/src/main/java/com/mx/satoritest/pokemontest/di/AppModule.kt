package com.mx.satoritest.pokemontest.di

import android.content.Context
import com.mx.satoritest.pokemontest.data.local.PokemonDatabase
import com.mx.satoritest.pokemontest.data.remote.PokeApiService
import com.mx.satoritest.pokemontest.data.repository.PokemonRepositoryImpl
import com.mx.satoritest.pokemontest.domain.repository.PokemonRepository
import com.mx.satoritest.pokemontest.domain.usescase.GetPokemonListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext appContext: Context): PokemonDatabase {
        return PokemonDatabase.getDatabase(appContext)
    }

    @Provides
    @Singleton
    fun providePokemonApi(): PokeApiService {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonRepository(
        api: PokeApiService,
        database: PokemonDatabase
    ): PokemonRepository {
        return PokemonRepositoryImpl(api, database.pokemonDao())
    }

    @Provides
    @Singleton
    fun provideGetPokemonListUseCase(repository: PokemonRepository): GetPokemonListUseCase {
        return GetPokemonListUseCase(repository)
    }
}
