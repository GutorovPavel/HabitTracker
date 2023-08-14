package com.example.habittracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.presentation.screens.detail.DetailScreen
import com.example.habittracker.presentation.screens.home.HomeScreen
import com.example.habittracker.presentation.screens.welcome.WelcomeScreen


@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Detail.route) {
            val currentHabit =
                navController.previousBackStackEntry?.savedStateHandle?.get<Habit>("habit")
            currentHabit?.let { DetailScreen(navController = navController, habit = it) }
        }
    }
}