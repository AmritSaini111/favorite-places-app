package com.example.favoriteplaces

import com.example.favoriteplaces.model.Place
import com.example.favoriteplaces.ui.viewmodel.FilterOption
import com.example.favoriteplaces.ui.viewmodel.PlacesViewModel
import com.example.favoriteplaces.ui.viewmodel.SortOption
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PlacesViewModelLogicTest {
    private val items = listOf(
        Place(id = 1, name = "Zoo", description = "", address = "Calgary", createdAt = 100, isFavorite = true, rating = 4),
        Place(id = 2, name = "Banff", description = "", address = "Alberta", createdAt = 300, isFavorite = false, rating = 5),
        Place(id = 3, name = "Cafe", description = "", address = "Downtown", createdAt = 200, isFavorite = true, rating = 2)
    )

    @Test fun sortByDate_returnsNewestFirst() { assertEquals(listOf(2, 3, 1), PlacesViewModel.applyFilters(items, "", SortOption.DATE, FilterOption.ALL).map { it.id }) }
    @Test fun sortByName_returnsAlphabetical() { assertEquals(listOf(2, 3, 1), PlacesViewModel.applyFilters(items, "", SortOption.NAME, FilterOption.ALL).map { it.id }) }
    @Test fun sortByRating_returnsHighestFirst() { assertEquals(listOf(2, 1, 3), PlacesViewModel.applyFilters(items, "", SortOption.RATING, FilterOption.ALL).map { it.id }) }
    @Test fun filterFavorites_returnsOnlyFavorites() { val r = PlacesViewModel.applyFilters(items, "", SortOption.DATE, FilterOption.FAVORITES); assertTrue(r.all { it.isFavorite }); assertEquals(2, r.size) }
    @Test fun searchByName_returnsMatchingPlace() { val r = PlacesViewModel.applyFilters(items, "ban", SortOption.DATE, FilterOption.ALL); assertEquals(1, r.size); assertEquals("Banff", r.first().name) }
    @Test fun searchByAddress_returnsMatchingPlace() { val r = PlacesViewModel.applyFilters(items, "down", SortOption.DATE, FilterOption.ALL); assertEquals(1, r.size); assertEquals(3, r.first().id) }
    @Test fun emptySearch_returnsAllPlaces() { assertEquals(3, PlacesViewModel.applyFilters(items, "", SortOption.DATE, FilterOption.ALL).size) }
    @Test fun searchWithNoMatch_returnsEmptyList() { assertTrue(PlacesViewModel.applyFilters(items, "xyz", SortOption.DATE, FilterOption.ALL).isEmpty()) }
    @Test fun favoritesAndSearch_canWorkTogether() { assertEquals(2, PlacesViewModel.applyFilters(items, "ca", SortOption.DATE, FilterOption.FAVORITES).size) }
    @Test fun ratingSortWithFavorites_filtersThenSorts() { assertEquals(listOf(1, 3), PlacesViewModel.applyFilters(items, "", SortOption.RATING, FilterOption.FAVORITES).map { it.id }) }
}
