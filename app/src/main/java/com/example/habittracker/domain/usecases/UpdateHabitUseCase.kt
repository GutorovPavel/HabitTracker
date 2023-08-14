package com.example.habittracker.domain.usecases

import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import javax.inject.Inject

class UpdateHabitUseCase @Inject constructor(
    private val repository: HabitsRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.updateHabit(id)
    }
}