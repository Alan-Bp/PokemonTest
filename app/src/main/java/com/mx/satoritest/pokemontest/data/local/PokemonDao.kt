package com.mx.satoritest.pokemontest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert
    suspend fun insertPokemons(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonEntity?

    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemons(): List<PokemonEntity>

    @Query("SELECT COUNT(*) FROM favorite_pokemons WHERE id = :id")
    suspend fun isFavorite(id: Int): Boolean

    @Insert
    suspend fun addFavorite(favorite: FavoritePokemonEntity)

    @Query("DELETE FROM favorite_pokemons WHERE id = :id")
    suspend fun removeFavorite(id: Int)
}