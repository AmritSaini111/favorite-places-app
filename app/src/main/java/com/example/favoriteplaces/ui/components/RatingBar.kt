package com.example.favoriteplaces.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    editable: Boolean = true
) {
    Row(modifier = Modifier.testTag("ratingBar")) {
        for (index in 1..5) {
            val icon = if (index <= rating) Icons.Filled.Star else Icons.Outlined.StarOutline
            Icon(
                imageVector = icon,
                contentDescription = "Rating $index",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .size(28.dp)
                    .then(if (editable) Modifier.clickable { onRatingChanged(index) } else Modifier)
            )
        }
    }
}
