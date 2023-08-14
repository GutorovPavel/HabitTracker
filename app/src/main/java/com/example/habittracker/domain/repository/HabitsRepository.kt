package com.example.habittracker.domain.repository

import com.example.habittracker.domain.models.Habit
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    suspend fun insertHabit(habit: Habit)
    suspend fun deleteHabit(habit: Habit)
    suspend fun updateHabit(id: Int)
    suspend fun getHabitById(id: Int): Habit?
    fun getAllHabits(): Flow<List<Habit>>

}