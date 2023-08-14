package com.example.habittracker.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.usecases.GetHabitByIdUseCase
import com.example.habittracker.domain.usecases.UpdateHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var currentHabit: Habit? = null

    init {
        getHabit(savedStateHandle)
    }

    private fun getHabit(savedStateHandle: SavedStateHandle) {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            viewModelScope.launch {
                currentHabit = getHabitByIdUseCase(noteId)
            }
        }
    }

    fun updateDay() {
        viewModelScope.launch {
            currentHabit?.id?.let { updateHabitUseCase(it) }
        }
    }

}