package com.mx.satoritest.pokemontest

import android.app.Application
import com.mx.satoritest.pokemontest.data.repository.AppContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokemonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainer.initialize(this)
    }
}