package com.example.favoriteplaces.ui.viewmodel

import com.example.favoriteplaces.model.Place

enum class SortOption {
    NEWEST,
    NAME,
    RATING
}

enum class FilterOption {
    ALL,
    FAVORITES
}

data class PlacesUiState(
    val places: List<Place> = emptyList(),
    val searchQuery: String = "",
    val sortOption: SortOption = SortOption.NEWEST,
    val filterOption: FilterOption = FilterOption.ALL,
    val isDarkMode: Boolean = false
)