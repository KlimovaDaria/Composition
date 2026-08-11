package com.example.composition.domain.entity

data class GameSettings(
    val maxSum: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
)