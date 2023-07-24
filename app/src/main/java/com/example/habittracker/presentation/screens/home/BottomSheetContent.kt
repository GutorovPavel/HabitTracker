package com.example.habittracker.presentation.screens.home

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.habittracker.domain.models.Habit
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    viewModel: HomeViewModel,
    onClose: () -> Unit
) {

    var title by remember { mutableStateOf(TextFieldValue("")) }
    var days by remember { mutableStateOf(TextFieldValue("")) }

    var enableNotify by remember { mutableStateOf(false) }
    val time = remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR]
    val minute = calendar[Calendar.MINUTE]

    val context = LocalContext.current

    val timePickerDialog = TimePickerDialog(
        context, { _, hour: Int, minute: Int ->
            time.value = "$hour:$minute"
        },
        hour, minute, true
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
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
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Medium,
            )
            OutlinedTextField(
                value = title,
                onValueChange = { newText ->
                    title = newText
                },
                singleLine = true,
                label = { Text("Название") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Количество дней: ")
                OutlinedTextField(
                    value = days,
                    onValueChange = {
                        days = it
                    },
                    singleLine = true,
                    placeholder = { Text("0") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    modifier = Modifier.width(110.dp)
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Напомнить уведомлением: ")
                Switch(checked = enableNotify, onCheckedChange = {
                    enableNotify = it
                })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (enableNotify) 1f else 0.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Время напоминания: ")
                OutlinedButton(
                    onClick = { timePickerDialog.show() },
                    enabled = enableNotify,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.width(110.dp)
                ) {
                    Text(text = time.value.ifEmpty { "Выбрать" })
                }
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

                    title = TextFieldValue("")
                    days = TextFieldValue("")
                    enableNotify = false
                    time.value = ""
                }
            ) {
                Text(text = "Отмена")
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .weight(1f),
                onClick = {
                    viewModel.addHabit(
                        Habit(
                            title = title.text,
                            daysGoal = days.text.toInt(),
                            notifyTime = (if (enableNotify) time.value else "")
                        )
                    )

                    onClose()

                    title = TextFieldValue("")
                    days = TextFieldValue("")
                    enableNotify = false
                    time.value = ""
                }
            ) {
                Text(text = "Начать")
            }
        }
    }
}
