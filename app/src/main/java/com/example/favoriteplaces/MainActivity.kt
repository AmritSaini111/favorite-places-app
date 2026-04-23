package com.example.favoriteplaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.favoriteplaces.data.local.PlacesDatabase
import com.example.favoriteplaces.data.preferences.ThemePreferences
import com.example.favoriteplaces.data.repository.RoomPlaceRepository
import com.example.favoriteplaces.navigation.AppNavGraph
import com.example.favoriteplaces.ui.theme.FavoritePlacesTheme
import com.example.favoriteplaces.ui.viewmodel.PlacesViewModel
import com.example.favoriteplaces.ui.viewmodel.PlacesViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PlacesDatabase.getDatabase(applicationContext)
        val repository = RoomPlaceRepository(database.placeDao())
        val themePreferences = ThemePreferences(applicationContext)
        val factory = PlacesViewModelFactory(repository, themePreferences)

        setContent {
            val vm: PlacesViewModel = viewModel(factory = factory)
            val isDarkMode by vm.isDarkMode.collectAsStateWithLifecycle()

            FavoritePlacesTheme(darkTheme = isDarkMode) {
                AppNavGraph(factory = factory)
            }
        }
    }
}