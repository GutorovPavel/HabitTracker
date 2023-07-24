package com.example.habittracker.data

import com.example.habittracker.data.local.db.HabitsDao
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import javax.inject.Inject

class HabitsRepositoryImpl @Inject constructor(
    private val dao: HabitsDao
): HabitsRepository {
    override suspend fun insertHabit(habit: Habit) {
        dao.insertHabit(habit)
    }

    override suspend fun deleteHabit(habit: Habit) {
        dao.deleteHabit(habit)
    }

    override suspend fun updateHabit(habit: Habit) {
        dao.updateHabit(habit)
    }

    override suspend fun getHabitById(id: Int): Habit? {
        return dao.getHabitById(id)
    }

    override suspend fun getAllHabits(): List<Habit> {
        return dao.getAllHabits()
    }
}