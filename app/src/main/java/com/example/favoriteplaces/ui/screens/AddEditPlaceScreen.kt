package com.example.favoriteplaces.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.favoriteplaces.model.Place
import com.example.favoriteplaces.ui.components.RatingBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPlaceScreen(
    existingPlace: Place?,
    onSave: (Place, Boolean) -> Unit,
    onBack: () -> Unit,
    errorMessage: String?
) {
    var name by remember(existingPlace) { mutableStateOf(existingPlace?.name ?: "") }
    var description by remember(existingPlace) { mutableStateOf(existingPlace?.description ?: "") }
    var address by remember(existingPlace) { mutableStateOf(existingPlace?.address ?: "") }
    var imageUri by remember(existingPlace) { mutableStateOf(existingPlace?.imageUri ?: "") }
    var latitudeText by remember(existingPlace) { mutableStateOf(existingPlace?.latitude?.toString() ?: "") }
    var longitudeText by remember(existingPlace) { mutableStateOf(existingPlace?.longitude?.toString() ?: "") }
    var isFavorite by remember(existingPlace) { mutableStateOf(existingPlace?.isFavorite ?: false) }
    var rating by remember(existingPlace) { mutableIntStateOf(existingPlace?.rating ?: 0) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri?.toString().orEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = if (existingPlace == null) "Add New Place" else "Edit Place",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = if (existingPlace == null)
                "Add your favorite location details below."
            else
                "Update the place information and save changes.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (!errorMessage.isNullOrBlank()) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(12.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Place Image",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { imagePicker.launch("image/*") }) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pick Image")
                }
                if (imageUri.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Image selected",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Basic Information", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Place Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        }

        Card(
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Location & Preference", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = latitudeText,
                    onValueChange = { latitudeText = it },
                    label = { Text("Latitude (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = longitudeText,
                    onValueChange = { longitudeText = it },
                    label = { Text("Longitude (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                HorizontalDivider()

                Text("Rating", style = MaterialTheme.typography.bodyLarge)
                RatingBar(
                    rating = rating,
                    onRatingChanged = { rating = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Mark as Favorite", style = MaterialTheme.typography.bodyLarge)
                    Switch(
                        checked = isFavorite,
                        onCheckedChange = { isFavorite = it }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val place = Place(
                    id = existingPlace?.id ?: 0,
                    name = name,
                    description = description,
                    address = address,
                    createdAt = existingPlace?.createdAt ?: System.currentTimeMillis(),
                    imageUri = imageUri,
                    latitude = latitudeText.toDoubleOrNull(),
                    longitude = longitudeText.toDoubleOrNull(),
                    isFavorite = isFavorite,
                    rating = rating
                )
                onSave(place, existingPlace != null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Text(if (existingPlace == null) "Save Place" else "Update Place")
        }

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}