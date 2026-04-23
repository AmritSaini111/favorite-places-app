package com.example.favoriteplaces.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favoriteplaces.data.preferences.ThemePreferences
import com.example.favoriteplaces.data.repository.PlaceRepository

class PlacesViewModelFactory(
    private val repository: PlaceRepository,
    private val themePreferences: ThemePreferences
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlacesViewModel::class.java)) {
            return PlacesViewModel(repository, themePreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}