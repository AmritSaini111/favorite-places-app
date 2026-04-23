package com.example.favoriteplaces.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.favoriteplaces.ui.viewmodel.FilterOption
import com.example.favoriteplaces.ui.viewmodel.SortOption

@Composable
fun SortFilterBar(
    selectedSort: SortOption,
    selectedFilter: FilterOption,
    onSortChanged: (SortOption) -> Unit,
    onFilterChanged: (FilterOption) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selectedSort == SortOption.NEWEST,
            onClick = { onSortChanged(SortOption.NEWEST) },
            label = { Text("Newest") }
        )

        FilterChip(
            selected = selectedSort == SortOption.NAME,
            onClick = { onSortChanged(SortOption.NAME) },
            label = { Text("Name") }
        )

        FilterChip(
            selected = selectedSort == SortOption.RATING,
            onClick = { onSortChanged(SortOption.RATING) },
            label = { Text("Rating") }
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        InputChip(
            selected = selectedFilter == FilterOption.ALL,
            onClick = { onFilterChanged(FilterOption.ALL) },
            label = { Text("All") }
        )

        InputChip(
            selected = selectedFilter == FilterOption.FAVORITES,
            onClick = { onFilterChanged(FilterOption.FAVORITES) },
            label = { Text("Favorites") }
        )
    }
}