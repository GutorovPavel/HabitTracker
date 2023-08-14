package com.example.habittracker.presentation.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.habittracker.domain.models.Habit

@Composable
fun DetailScreen(
    navController: NavController,
    habit: Habit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "${habit.title} ${habit.days}/${habit.daysGoal}")
            Button(onClick = {
                viewModel.updateDay()
            }) {
                Text(text = "Выполнить")
            }
        }
    }
}