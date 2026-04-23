package com.example.favoriteplaces.navigation

sealed class AppDestination(val route: String) {
    data object Splash : AppDestination("splash")
    data object List : AppDestination("list")
    data object Add : AppDestination("add")
    data object Details : AppDestination("details/{placeId}") {
        fun createRoute(placeId: Int) = "details/$placeId"
    }
    data object Edit : AppDestination("edit/{placeId}") {
        fun createRoute(placeId: Int) = "edit/$placeId"
    }
}