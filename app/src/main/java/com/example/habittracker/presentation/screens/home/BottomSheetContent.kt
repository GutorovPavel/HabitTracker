package com.example.habittracker.presentation.screens.home

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun BottomSheetContent(
    viewModel: HomeViewModel,
    onAdd: () -> Unit,
    onClose: () -> Unit
) {
    val title = viewModel.title.value
    val days = viewModel.daysGoal.value
    val enableNotify = viewModel.toNotify.value
    val time = viewModel.time.value


    var state by remember { mutableStateOf<Hours>(FullHours(12, 43)) }

    var isTitleError by rememberSaveable { mutableStateOf(false) }
    val showDialog = rememberSaveable { mutableStateOf(false) }

//    var title by remember { mutableStateOf(TextFieldValue("")) }
//    var days by remember { mutableStateOf(TextFieldValue("")) }
//
//    var enableNotify by remember { mutableStateOf(false) }
//    val time = remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR]
    val minute = calendar[Calendar.MINUTE]

    val context = LocalContext.current

    val timePickerDialog = TimePickerDialog(
        context, { _, hour: Int, minute: Int ->
            viewModel.onTimeChange("$hour:$minute")
        },
        hour, minute, true
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Новая цель",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Medium,
            )
            OutlinedTextField(
                value = title.text,
                onValueChange = { newText ->
                    if (newText.isNotEmpty()) isTitleError = false
                    viewModel.onTitleChanged(newText)
                },
                label = { Text(text = title.hint) },
                singleLine = true,
                isError = isTitleError,
                supportingText = {
                    if (isTitleError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Название не может быть пустым",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Количество дней: ")
                OutlinedTextField(
                    value = days.text,
                    onValueChange = { viewModel.onDaysChanged(it) },
                    singleLine = true,
                    placeholder = { Text(text = days.hint) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    modifier = Modifier.width(110.dp)
                )
//                TextButton(onClick = { showDialog.value = true }) {
//                    Text(text = days.toString())
//                }
//                NumberPicker(
//                    value = days,
//                    onValueChange = { viewModel.onDaysChanged(it) },
//                    range = 1..100
//                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (enableNotify) 1f else 0.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Время напоминания: ")
                    TextButton(
                        onClick = { timePickerDialog.show() },
                        enabled = enableNotify,
                        modifier = Modifier.width(110.dp)
                    ) {
                        Text(text = time.ifEmpty { "Выбрать" })
                    }
//                HoursNumberPicker(
//                    value = state,
//                    onValueChange = {
//                        state = it
//                        viewModel.onTimeChange("${state.hours}:${state.minutes}")
//                    },
//                    hoursDivider = {
//                        Text(
//                            modifier = Modifier.size(24.dp),
//                            textAlign = TextAlign.Center,
//                            text = ":"
//                        )
//                    },
//                )
            }

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Напомнить уведомлением: ")
                Switch(checked = enableNotify, onCheckedChange = {
                    viewModel.onNotifyStatusChanged(it)
                })
            }
        }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilledTonalButton(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .weight(1f),
                    onClick = {
                        onClose()
                        viewModel.clear()
                    }
                ) {
                    Text(text = "Отмена")
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .weight(1f),
                    onClick = {
                        if (title.text.isEmpty()) isTitleError = true
                        else {
                            onAdd()
                            onClose()
                            viewModel.clear()
                        }
                    }
                ) {
                    Text(text = "Начать")
                }
            }
        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberPickerPopup(
    state: MutableState<Boolean>,
    days: Int,
    onValueChange:(Int) -> Unit
) {
    if (state.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                state.value = false
            },
            text = {
                NumberPicker(
                    value = days,
                    onValueChange = { onValueChange(it) },
                    range = 1..100
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        state.value = false
                    }
                ) {
                    Text("Выбрать")
                }
            }
        )
    }
}
