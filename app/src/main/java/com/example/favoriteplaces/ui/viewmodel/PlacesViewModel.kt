package com.example.favoriteplaces.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favoriteplaces.data.repository.PlaceRepository
import com.example.favoriteplaces.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

interface ThemeSettings {
    val isDarkMode: StateFlow<Boolean>
    suspend fun setDarkMode(enabled: Boolean)
}

class PlacesViewModel(
    private val repository: PlaceRepository,
    private val themePreferences: ThemeSettings
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val sortOption = MutableStateFlow(SortOption.NEWEST)
    private val filterOption = MutableStateFlow(FilterOption.ALL)

    val isDarkMode: StateFlow<Boolean> = themePreferences.isDarkMode

    val uiState: StateFlow<PlacesUiState> =
        combine(
            repository.getAllPlaces(),
            searchQuery,
            sortOption,
            filterOption,
            isDarkMode
        ) { places: List<Place>,
            query: String,
            sort: SortOption,
            filter: FilterOption,
            darkMode: Boolean ->

            val sortedPlaces = applyFilters(
                places = places,
                query = query,
                sort = sort,
                filter = filter
            )

            PlacesUiState(
                places = sortedPlaces,
                searchQuery = query,
                sortOption = sort,
                filterOption = filter,
                isDarkMode = darkMode
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PlacesUiState()
        )

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun updateSort(option: SortOption) {
        sortOption.value = option
    }

    fun updateFilter(option: FilterOption) {
        filterOption.value = option
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            themePreferences.setDarkMode(!isDarkMode.value)
        }
    }

    fun savePlace(
        place: Place,
        isEdit: Boolean,
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (place.name.isBlank()) {
            onError("Place name is required")
            return
        }

        if (place.address.isBlank()) {
            onError("Address is required")
            return
        }

        viewModelScope.launch {
            if (isEdit) {
                repository.updatePlace(place)
            } else {
                repository.insertPlace(
                    place.copy(createdAt = System.currentTimeMillis())
                )
            }
            onComplete()
        }
    }

    fun deletePlace(place: Place, onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.deletePlace(place)
            onComplete()
        }
    }

    companion object {
        fun applyFilters(
            places: List<Place>,
            query: String,
            sort: SortOption,
            filter: FilterOption
        ): List<Place> {
            val searchedPlaces = places.filter { place ->
                place.name.contains(query, ignoreCase = true) ||
                        place.address.contains(query, ignoreCase = true)
            }

            val filteredPlaces = when (filter) {
                FilterOption.ALL -> searchedPlaces
                FilterOption.FAVORITES -> searchedPlaces.filter { it.isFavorite }
            }

            return when (sort) {
                SortOption.NEWEST -> filteredPlaces.sortedByDescending { it.createdAt }
                SortOption.NAME -> filteredPlaces.sortedBy { it.name.lowercase() }
                SortOption.RATING -> filteredPlaces.sortedByDescending { it.rating }
            }
        }
    }
}