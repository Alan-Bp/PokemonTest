package com.mx.satoritest.pokemontest.domain.usescase

import com.mx.satoritest.pokemontest.domain.model.Pokemon
import com.mx.satoritest.pokemontest.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(offset: Int, limit: Int): List<Pokemon> {
        return repository.getPokemonList(offset, limit)
    }
}