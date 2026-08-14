package com.example.composition.domain.entity

import java.io.Serializable

data class GameSettings(
    val maxSum: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
): Serializable