package com.mx.satoritest.pokemontest.domain.usescase

import com.mx.satoritest.pokemontest.domain.model.Pokemon
import com.mx.satoritest.pokemontest.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(offset: Int, limit: Int): Flow<List<Pokemon>> = flow {
        emit(repository.getPokemonList(offset, limit))
    }
}
