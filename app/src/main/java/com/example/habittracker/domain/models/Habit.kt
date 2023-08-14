package com.example.habittracker.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.habittracker.util.HabitStatus
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val days: Int = 0,
    val daysGoal: Int,
    val notifyTime: String? = "",
    val status: String = HabitStatus.Active.status
): Parcelable