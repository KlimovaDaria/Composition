package com.example.composition.data

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.repository.GameRepository
import kotlin.random.Random


class GameRepositoryImpl : GameRepository {

    private companion object {
        const val MIN_SUM_VALUE = 2
        const val MIN_VISIBLE_VALUE = 1
    }

    override fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(1, sum)
        val options = mutableSetOf<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = (rightAnswer - countOfOptions).coerceAtLeast(MIN_VISIBLE_VALUE)
        val to = maxSumValue.coerceAtMost(rightAnswer + countOfOptions)
        while (options.size != countOfOptions) {
            val item = Random.nextInt(from, to + 1)
            options.add(item)
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> GameSettings(
                10,
                3,
                50,
                8
            )

            Level.EASY -> {
                GameSettings(
                    10,
                    10,
                    70,
                    60
                )
            }

            Level.NORMAL -> {
                GameSettings(
                    20,
                    20,
                    80,
                    40
                )
            }

            Level.HARD -> {
                GameSettings(
                    30,
                    30,
                    90,
                    40
                )
            }
        }
    }

}