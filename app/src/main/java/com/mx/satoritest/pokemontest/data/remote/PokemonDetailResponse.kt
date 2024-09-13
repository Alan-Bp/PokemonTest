package com.mx.satoritest.pokemontest.data.remote

import com.mx.satoritest.pokemontest.domain.model.Pokemon

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<TypeSlot>
)

data class Sprites(
    val frontDefault: String
)

data class TypeSlot(
    val type: Type
)

data class Type(
    val name: String
)

fun PokemonDetailResponse.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        name = this.name,
        imageUrl = sprites.frontDefault,
        height = height.toDouble(),
        weight = weight.toDouble(),
        types = types.map { it.type.name }
    )
}

