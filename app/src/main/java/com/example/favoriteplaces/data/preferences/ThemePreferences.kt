package com.example.favoriteplaces.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.favoriteplaces.ui.viewmodel.ThemeSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private val Context.dataStore by preferencesDataStore(name = "settings")

class ThemePreferences(private val context: Context) : ThemeSettings {

    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override val isDarkMode: StateFlow<Boolean> =
        context.dataStore.data
            .map { prefs ->
                prefs[DARK_MODE_KEY] ?: false
            }
            .stateIn(
                scope,
                SharingStarted.Eagerly,
                false
            )

    override suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE_KEY] = enabled
        }
    }
}