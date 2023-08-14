package com.example.habittracker.presentation.navigation

sealed class Screen(val route: String) {
    object Welcome: Screen("welcome_screen")
    object Home: Screen("home_screen")
    object Detail: Screen("detail_screen")
}
