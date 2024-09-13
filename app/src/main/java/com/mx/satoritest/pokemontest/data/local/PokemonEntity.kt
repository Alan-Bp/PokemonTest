package com.mx.satoritest.pokemontest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mx.satoritest.pokemontest.domain.model.Pokemon

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Double,
    val weight: Double,
    val types: String
) {
    fun toPokemon(): Pokemon {
        return Pokemon(
            id = id,
            name = name,
            imageUrl = imageUrl,
            height = height,
            weight = weight,
            types = types.split(",")
        )
    }
}

@Entity(tableName = "favorite_pokemons")
data class FavoritePokemonEntity(
    @PrimaryKey val id: Int
)