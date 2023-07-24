package com.example.habittracker.presentation.screens.home

import com.example.habittracker.domain.models.Habit

data class HomeState(
    val isLoading: Boolean = false,
    val habits: List<Habit> = emptyList(),
    val error: String = ""
)
