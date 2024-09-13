package com.mx.satoritest.pokemontest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mx.satoritest.pokemontest.domain.repository.PokemonRepository


class ViewModelFactory(
    private val repository: PokemonRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            return PokemonDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
