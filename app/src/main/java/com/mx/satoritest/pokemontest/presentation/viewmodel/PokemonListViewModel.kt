package com.mx.satoritest.pokemontest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.satoritest.pokemontest.domain.model.Pokemon
import com.mx.satoritest.pokemontest.domain.repository.PokemonRepository
import com.mx.satoritest.pokemontest.domain.usescase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var offset = 0
    private val limit = 25

    init {
        loadPokemonList()
    }

    private fun loadPokemonList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val pokemons = getPokemonListUseCase.invoke(offset, limit)
                _pokemonList.value += pokemons
                offset += limit
            } catch (e: Exception) {
                //ABP: Manejar el error*
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadMorePokemons() {
        if (!isLoading.value) {
            loadPokemonList()
        }
    }

    suspend fun isFavorite(pokemonId: Int): Boolean {
        return pokemonRepository.isFavorite(pokemonId)
    }
}
