package com.example.favoriteplaces.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.favoriteplaces.model.Place
import com.example.favoriteplaces.ui.components.DeleteConfirmDialog
import com.example.favoriteplaces.ui.screens.AddEditPlaceScreen
import com.example.favoriteplaces.ui.screens.PlaceDetailsScreen
import com.example.favoriteplaces.ui.screens.PlacesListScreen
import com.example.favoriteplaces.ui.screens.SplashScreen
import com.example.favoriteplaces.ui.viewmodel.PlacesViewModel
import com.example.favoriteplaces.ui.viewmodel.PlacesViewModelFactory

@Composable
fun AppNavGraph(factory: PlacesViewModelFactory) {
    val navController = rememberNavController()
    val viewModel: PlacesViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val feedbackMessage = remember { mutableStateOf<String?>(null) }
    val formErrorMessage = remember { mutableStateOf<String?>(null) }
    val placeToDelete = remember { mutableStateOf<Place?>(null) }

    if (placeToDelete.value != null) {
        DeleteConfirmDialog(
            onDismiss = { placeToDelete.value = null },
            onConfirm = {
                val currentPlace = placeToDelete.value
                if (currentPlace != null) {
                    viewModel.deletePlace(currentPlace) {
                        feedbackMessage.value = "Place deleted"
                        placeToDelete.value = null
                        navController.navigate(AppDestination.List.route) {
                            popUpTo(AppDestination.List.route) { inclusive = true }
                        }
                    }
                }
            }
        )
    }

    NavHost(
        navController = navController,
        startDestination = AppDestination.Splash.route
    ) {
        composable(AppDestination.Splash.route) {
            SplashScreen(
                onGetStarted = {
                    navController.navigate(AppDestination.List.route)
                }
            )
        }

        composable(AppDestination.List.route) {
            PlacesListScreen(
                uiState = uiState,
                message = feedbackMessage.value,
                onMessageConsumed = { feedbackMessage.value = null },
                onAddClick = { navController.navigate(AppDestination.Add.route) },
                onPlaceClick = { place ->
                    navController.navigate(AppDestination.Details.createRoute(place.id))
                },
                onSearchChanged = viewModel::updateSearchQuery,
                onSortChanged = viewModel::updateSort,
                onFilterChanged = viewModel::updateFilter,
                onToggleDarkMode = viewModel::toggleDarkMode
            )
        }

        composable(AppDestination.Add.route) {
            AddEditPlaceScreen(
                existingPlace = null,
                onSave = { place, isEdit ->
                    viewModel.savePlace(
                        place = place,
                        isEdit = isEdit,
                        onComplete = {
                            feedbackMessage.value = "Place saved"
                            navController.popBackStack()
                        },
                        onError = { formErrorMessage.value = it }
                    )
                },
                onBack = { navController.popBackStack() },
                errorMessage = formErrorMessage.value.also { formErrorMessage.value = null }
            )
        }

        composable(
            route = AppDestination.Edit.route,
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getInt("placeId") ?: 0
            val place = uiState.places.firstOrNull { it.id == placeId }

            if (place != null) {
                AddEditPlaceScreen(
                    existingPlace = place,
                    onSave = { updatedPlace, isEdit ->
                        viewModel.savePlace(
                            place = updatedPlace,
                            isEdit = isEdit,
                            onComplete = {
                                feedbackMessage.value = "Place updated"
                                navController.popBackStack()
                            },
                            onError = { formErrorMessage.value = it }
                        )
                    },
                    onBack = { navController.popBackStack() },
                    errorMessage = formErrorMessage.value.also { formErrorMessage.value = null }
                )
            }
        }

        composable(
            route = AppDestination.Details.route,
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getInt("placeId") ?: 0
            val place = uiState.places.firstOrNull { it.id == placeId }

            if (place != null) {
                PlaceDetailsScreen(
                    place = place,
                    onEditClick = {
                        navController.navigate(AppDestination.Edit.createRoute(placeId))
                    },
                    onDeleteClick = { placeToDelete.value = place },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}