package com.example.habittracker.presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.domain.models.Habit

@Composable
fun HabitItem(
    habit: Habit,
    onClick: (Habit) -> Unit
) {
    Card (
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.clickable { onClick(habit) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = habit.title,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                if (habit.notifyTime != null) {
                    Text(text = habit.notifyTime)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "Дней подряд:",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "${habit.days}/${habit.daysGoal}",
//                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "Статус:" ,
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = habit.status,
//                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                progress = (habit.days.toFloat()/habit.daysGoal.toFloat())
            )
        }
    }
}