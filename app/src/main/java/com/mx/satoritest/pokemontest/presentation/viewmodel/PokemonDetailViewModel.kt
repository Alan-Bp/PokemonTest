package com.mx.satoritest.pokemontest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.satoritest.pokemontest.domain.model.Pokemon
import com.mx.satoritest.pokemontest.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private val _pokemonDetail = MutableStateFlow<Pokemon?>(null)
    val pokemonDetail: StateFlow<Pokemon?> = _pokemonDetail

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun loadPokemonDetail(id: Int) {
        viewModelScope.launch {
            val detail = pokemonRepository.getPokemonDetails(id)
            _pokemonDetail.value = detail
        }
    }

    fun checkIfFavorite(id: Int) {
        viewModelScope.launch {
            val favorite = pokemonRepository.isFavorite(id)
            _isFavorite.value = favorite
        }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            val currentFavoriteState = _isFavorite.value
            if (currentFavoriteState) {
                pokemonRepository.removeFavorite(id)
            } else {
                pokemonRepository.addFavorite(id)
            }
            _isFavorite.value = !currentFavoriteState
        }
    }

}
