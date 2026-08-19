package com.example.composition.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.composition.R
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.usecases.GenerateQuestionUseCase
import com.example.composition.domain.usecases.GetGameSettingsUseCase


class GameFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = GameRepositoryImpl()
    private val generateQuestionUseCase = GenerateQuestionUseCase(repo)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repo)
    private val context = application

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val _questionLD = MutableLiveData<Question>()
    val questionLD: LiveData<Question>
        get() = _questionLD

    private val _timerStrLD = MutableLiveData<String>()
    val timerStrLD: LiveData<String>
        get() = _timerStrLD

    private var timer: CountDownTimer? = null

    private var countOfRightAnswer = 0
    private var countOfQuestions = 0

    private val _percentOfRightAnswersLD = MutableLiveData<Int>()
    val percentOfRightAnswersLD: LiveData<Int>
        get() = _percentOfRightAnswersLD

    private val _progressAnswersLD = MutableLiveData<String>()
    val progressAnswersLD: LiveData<String>
        get() = _progressAnswersLD

    private val _enoughCountOfRightAnswersLD = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswersLD: LiveData<Boolean>
        get() = _enoughCountOfRightAnswersLD

    private val _enoughPercentsOfRightAnswersLD = MutableLiveData<Boolean>()
    val enoughPercentsOfRightAnswersLD: LiveData<Boolean>
        get() = _enoughCountOfRightAnswersLD

    private val _minPercentLD = MutableLiveData<Int>()
    val minPercentLD: LiveData<Int>
        get() = _minPercentLD

    private val _gameResultLD = MutableLiveData<GameResult>()
    val gameResultLD: LiveData<GameResult>
        get() = _gameResultLD

    fun startGame(level: Level) {
        getGameSettings(level)
        generateQuestion()
        startTimer()
    }

    private fun getGameSettings(level: Level) {
        this.gameSettings = getGameSettingsUseCase(level)
        this.level = level
        _minPercentLD.value = gameSettings.minPercentOfRightAnswers
    }

    fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onFinish() {
                finishGame()
            }

            override fun onTick(p0: Long) {
                _timerStrLD.value = formatTime(p0)
            }

        }
        timer?.start()
    }

    private fun finishGame() {
        val gameResult = GameResult(
            winner = _enoughPercentsOfRightAnswersLD.value==true && _enoughCountOfRightAnswersLD.value==true,
            countOfRightAnswers = countOfRightAnswer,
            countOfAnswers = countOfQuestions,
            gameSettings = gameSettings
        )
        _gameResultLD.value = gameResult
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    @SuppressLint("DefaultLocale")
    private fun formatTime(millisUntilFinish: Long): String {
        val seconds = millisUntilFinish / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTE
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTE)
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun generateQuestion() {
        val question = generateQuestionUseCase(gameSettings.maxSum)
        _questionLD.value = question
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = calcPercentOfRightAnswers()
        _percentOfRightAnswersLD.value = percent
        _progressAnswersLD.value = String.format(
            context.resources.getString(R.string.right_answers),
            countOfRightAnswer,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCountOfRightAnswersLD.value =
            countOfRightAnswer >= gameSettings.minCountOfRightAnswers
        _enoughPercentsOfRightAnswersLD.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calcPercentOfRightAnswers(): Int {
        return (countOfRightAnswer.toDouble() / countOfQuestions * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        if (number == _questionLD.value?.rightAnswer) {
            countOfRightAnswer++
        }
        countOfQuestions++
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTE = 60
    }
}