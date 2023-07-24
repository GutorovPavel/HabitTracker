package com.example.habittracker.domain.repository

import com.example.habittracker.domain.models.Habit

interface HabitsRepository {

    suspend fun insertHabit(habit: Habit)
    suspend fun deleteHabit(habit: Habit)
    suspend fun updateHabit(habit: Habit)
    suspend fun getHabitById(id: Int): Habit?
    suspend fun getAllHabits(): List<Habit>

}