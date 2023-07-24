package com.example.habittracker.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.usecases.AddHabitUseCase
import com.example.habittracker.domain.usecases.DeleteHabitUseCase
import com.example.habittracker.domain.usecases.GetAllHabitsUseCase
import com.example.habittracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllHabitsUseCase: GetAllHabitsUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase
): ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _habits = MutableStateFlow(emptyList<Habit>())
    val habits = _habits.asStateFlow()

    private var deletedHabit: Habit? = null

    init {
        getAllHabits()
    }

    private fun getAllHabits() {
        getAllHabitsUseCase().onEach {
            when(it) {
                is Resource.Success -> {
                    _state.value = HomeState(habits = it.data?: emptyList())
                    _habits.value = _state.value.habits
                }
                is Resource.Error -> {
                    _state.value = HomeState(error = it.message ?: "Oops...")
                }
                is Resource.Loading -> {
                    _state.value = HomeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            addHabitUseCase(habit)
            deletedHabit = habit
        }
        getAllHabits()
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            deleteHabitUseCase(habit)
        }
        getAllHabits()
    }
}