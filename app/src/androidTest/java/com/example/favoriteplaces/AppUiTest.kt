package com.example.favoriteplaces

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.favoriteplaces.model.Place
import com.example.favoriteplaces.ui.screens.PlaceDetailsScreen
import com.example.favoriteplaces.ui.screens.SplashScreen
import org.junit.Rule
import org.junit.Test

class AppUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun splashScreen_showsWelcomeText() {
        composeTestRule.setContent {
            SplashScreen(onGetStarted = {})
        }
        composeTestRule.onNodeWithText("Welcome to Favorite Places").assertIsDisplayed()
    }

    @Test
    fun splashScreen_showsGetStartedButton() {
        composeTestRule.setContent {
            SplashScreen(onGetStarted = {})
        }
        composeTestRule.onNodeWithText("Get Started").assertIsDisplayed()
    }

    @Test
    fun splashScreen_buttonCanBeClicked() {
        composeTestRule.setContent {
            SplashScreen(onGetStarted = {})
        }
        composeTestRule.onNodeWithText("Get Started").performClick()
    }

    @Test
    fun detailsScreen_showsPlaceName() {
        composeTestRule.setContent {
            PlaceDetailsScreen(
                place = samplePlace(),
                onEditClick = {},
                onDeleteClick = {},
                onBackClick = {}
            )
        }
        composeTestRule.onNodeWithText("Tim Hortons").assertIsDisplayed()
    }

    @Test
    fun detailsScreen_showsDescription() {
        composeTestRule.setContent {
            PlaceDetailsScreen(
                place = samplePlace(),
                onEditClick = {},
                onDeleteClick = {},
                onBackClick = {}
            )
        }
        composeTestRule.onNodeWithText("Nice and cozy coffee place").assertIsDisplayed()
    }

    @Test
    fun detailsScreen_showsAddress() {
        composeTestRule.setContent {
            PlaceDetailsScreen(
                place = samplePlace(),
                onEditClick = {},
                onDeleteClick = {},
                onBackClick = {}
            )
        }
        composeTestRule.onNodeWithText("Calgary, AB").assertIsDisplayed()
    }

    @Test
    fun detailsScreen_showsMapsButton() {
        composeTestRule.setContent {
            PlaceDetailsScreen(
                place = samplePlace(),
                onEditClick = {},
                onDeleteClick = {},
                onBackClick = {}
            )
        }
        composeTestRule.onNodeWithText("Open in Google Maps").assertIsDisplayed()
    }

    @Test
    fun detailsScreen_showsEditButton() {
        composeTestRule.setContent {
            PlaceDetailsScreen(
                place = samplePlace(),
                onEditClick = {},
                onDeleteClick = {},
                onBackClick = {}
            )
        }
        composeTestRule.onNodeWithText("Edit").assertIsDisplayed()
    }

    @Test
    fun detailsScreen_showsDeleteButton() {
        composeTestRule.setContent {
            PlaceDetailsScreen(
                place = samplePlace(),
                onEditClick = {},
                onDeleteClick = {},
                onBackClick = {}
            )
        }
        composeTestRule.onNodeWithText("Delete").assertIsDisplayed()
    }

    @Test
    fun detailsScreen_showsBackButton() {
        composeTestRule.setContent {
            PlaceDetailsScreen(
                place = samplePlace(),
                onEditClick = {},
                onDeleteClick = {},
                onBackClick = {}
            )
        }
        composeTestRule.onNodeWithText("Back").assertIsDisplayed()
    }

    private fun samplePlace() = Place(
        id = 1,
        name = "Tim Hortons",
        description = "Nice and cozy coffee place",
        address = "Calgary, AB",
        createdAt = System.currentTimeMillis(),
        imageUri = "",
        latitude = 51.0447,
        longitude = -114.0719,
        isFavorite = true,
        rating = 4
    )
}