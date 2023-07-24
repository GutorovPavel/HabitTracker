package com.example.habittracker.domain.usecases

import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import com.example.habittracker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllHabitsUseCase (
    private val repository: HabitsRepository
){
    operator fun invoke(): Flow<Resource<List<Habit>>> = flow {

        try {
            emit(Resource.Loading())
            val habits = repository.getAllHabits()
            emit(Resource.Success(habits))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}