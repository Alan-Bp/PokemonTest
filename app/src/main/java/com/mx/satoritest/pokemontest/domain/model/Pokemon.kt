package com.mx.satoritest.pokemontest.domain.model

import com.mx.satoritest.pokemontest.data.local.PokemonEntity

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Double,
    val weight: Double,
    val types: List<String>
) {
    fun toEntity(): PokemonEntity {
        return PokemonEntity(
            id = id,
            name = name,
            imageUrl = imageUrl,
            height = height,
            weight = weight,
            types = types.joinToString(",")
        )
    }
}


