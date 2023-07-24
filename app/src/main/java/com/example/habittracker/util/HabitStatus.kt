package com.example.habittracker.util

sealed class HabitStatus(val status: String) {
    object Active: HabitStatus("Активно")
    object Completed: HabitStatus("Завершено")
    object Failed: HabitStatus("Провалено")
}
