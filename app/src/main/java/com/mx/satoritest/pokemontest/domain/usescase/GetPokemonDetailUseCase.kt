package com.mx.satoritest.pokemontest.domain.usescase

import com.mx.satoritest.pokemontest.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
}