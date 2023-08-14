package com.example.habittracker.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habittracker.domain.models.Habit
import kotlinx.coroutines.flow.Flow


@Dao
interface HabitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("UPDATE habit SET days = days + 1 WHERE id LIKE :id")
    suspend fun updateHabit(id: Int)

    @Query("SELECT * FROM habit WHERE id = :id")
    suspend fun getHabitById(id: Int): Habit?

    @Query("SELECT * FROM habit")
    fun getAllHabits(): Flow<List<Habit>>
}