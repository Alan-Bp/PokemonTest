package com.mx.satoritest.pokemontest.data.remote

import com.google.gson.annotations.SerializedName
import com.mx.satoritest.pokemontest.domain.model.Pokemon

data class PokemonListResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    val results: List<PokemonResult>
)

fun mapToDomain(
    pokemonDetailResponse: PokemonDetailResponse,
    height: Double,
    weight: Double,
    types: List<String>
): Pokemon {
    return Pokemon(
        id = pokemonDetailResponse.id,
        name = pokemonDetailResponse.name,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonDetailResponse.id}.png",
        height = height,
        weight = weight,
        types = types
    )
}

data class PokemonResult(
    val url: String
)