package com.example.favoriteplaces.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.favoriteplaces.model.Place
import com.example.favoriteplaces.ui.components.EmptyState
import com.example.favoriteplaces.ui.components.PlaceCard
import com.example.favoriteplaces.ui.components.PlacesSearchBar
import com.example.favoriteplaces.ui.components.SortFilterBar
import com.example.favoriteplaces.ui.viewmodel.FilterOption
import com.example.favoriteplaces.ui.viewmodel.PlacesUiState
import com.example.favoriteplaces.ui.viewmodel.SortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesListScreen(
    uiState: PlacesUiState,
    message: String?,
    onMessageConsumed: () -> Unit,
    onAddClick: () -> Unit,
    onPlaceClick: (Place) -> Unit,
    onSearchChanged: (String) -> Unit,
    onSortChanged: (SortOption) -> Unit,
    onFilterChanged: (FilterOption) -> Unit,
    onToggleDarkMode: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        if (!message.isNullOrBlank()) {
            snackbarHostState.showSnackbar(message)
            onMessageConsumed()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Favorite Places") },
                actions = {
                    IconButton(
                        onClick = onToggleDarkMode,
                        modifier = Modifier.testTag("themeToggle")
                    ) {
                        Icon(
                            imageVector = if (uiState.isDarkMode) {
                                Icons.Outlined.LightMode
                            } else {
                                Icons.Outlined.DarkMode
                            },
                            contentDescription = "Toggle theme"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                modifier = Modifier.testTag("addPlaceFab")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add place")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            if (uiState.places.isEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(top = 16.dp)
                ) {
                    item {
                        PlacesSearchBar(
                            query = uiState.searchQuery,
                            onQueryChanged = onSearchChanged
                        )
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        SortFilterBar(
                            selectedSort = uiState.sortOption,
                            selectedFilter = uiState.filterOption,
                            onSortChanged = onSortChanged,
                            onFilterChanged = onFilterChanged
                        )
                    }
                    item {
                        AnimatedVisibility(visible = true) {
                            EmptyState()
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
                        PlacesSearchBar(
                            query = uiState.searchQuery,
                            onQueryChanged = onSearchChanged
                        )
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        SortFilterBar(
                            selectedSort = uiState.sortOption,
                            selectedFilter = uiState.filterOption,
                            onSortChanged = onSortChanged,
                            onFilterChanged = onFilterChanged
                        )
                    }
                    items(uiState.places, key = { it.id }) { place ->
                        PlaceCard(
                            place = place,
                            onClick = { onPlaceClick(place) }
                        )
                    }
                }
            }
        }
    }
}