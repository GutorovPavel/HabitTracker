package com.example.habittracker.util

import androidx.annotation.DrawableRes
import com.example.habittracker.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First: OnBoardingPage(image = R.drawable.first_page, title = "Формирование привычки", description = "desc 1")
    object Second: OnBoardingPage(image = R.drawable.second_page, title = "Поставьте цель", description = "desc 2")
    object Third: OnBoardingPage(image = R.drawable.third_page, title = "Регулярность", description = "desc 3")
}
