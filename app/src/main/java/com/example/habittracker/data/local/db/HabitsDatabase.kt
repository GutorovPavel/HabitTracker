package com.example.habittracker.data.local.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habittracker.domain.models.Habit


@Database(
    entities = [
        Habit::class
    ],
    version = 1
)
abstract class HabitsDatabase: RoomDatabase() {
    abstract val dao: HabitsDao
}