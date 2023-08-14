package com.example.habittracker.domain.usecases

import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import com.example.habittracker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllHabitsUseCase @Inject constructor (
    private val repository: HabitsRepository
) {
    operator fun invoke(): Flow<List<Habit>>  {

        return repository.getAllHabits()
    }
}