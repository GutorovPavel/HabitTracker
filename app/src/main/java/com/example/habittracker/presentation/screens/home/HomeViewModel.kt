package com.example.habittracker.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.usecases.AddHabitUseCase
import com.example.habittracker.domain.usecases.DeleteHabitUseCase
import com.example.habittracker.domain.usecases.GetAllHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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


    //  TextFields
    private val _title = mutableStateOf(TextValueState(hint = "Введите название.."))
    val title: State<TextValueState> = _title

    private val _daysGoal = mutableStateOf(TextValueState(text = "21", hint = "0"))
    val daysGoal: State<TextValueState> = _daysGoal

    private val _toNotify = mutableStateOf(false)
    val toNotify: State<Boolean> = _toNotify

    private val _time = mutableStateOf("")
    val time: State<String> = _time


    private var getHabitsJob: Job? = null


    init {
        getAllHabits()
    }

    private fun getAllHabits() {
        getHabitsJob?.cancel()
        getHabitsJob = getAllHabitsUseCase().onEach {
            _habits.value = it
        }.launchIn(viewModelScope)
    }

    fun onTitleChanged(new: String) {
        _title.value = title.value.copy(
            text = new
        )
    }
    fun onDaysChanged(new: String) {
        _daysGoal.value = daysGoal.value.copy(
            text = new
        )
    }
    fun onNotifyStatusChanged(new: Boolean) {
        _toNotify.value = new
    }

    fun onTimeChange(new: String) {
        _time.value = new
    }

    fun clear() {
        onTitleChanged("")
        onDaysChanged("21")
        onNotifyStatusChanged(false)
        onTimeChange("")
    }

    fun addHabit() {
        viewModelScope.launch {
            addHabitUseCase(
                Habit(
                    title = _title.value.text,
                    daysGoal = _daysGoal.value.text.toInt(),
                    notifyTime = if (_toNotify.value) _time.value else ""
                )
            )
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