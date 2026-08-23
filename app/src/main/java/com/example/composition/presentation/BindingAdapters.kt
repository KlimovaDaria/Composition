package com.example.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult


@BindingAdapter("requiredCountAnswers")
fun bindRequiredCountAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("countOfRightAnswers")
fun bindCountOfRightAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("requiredPercentageAnswers")
fun bindRequiredPercentageAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )
}

@BindingAdapter("percentageOfRightAnswers")
fun bindPercentageOfRightAnswers(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercentageOfRightAnswers(gameResult)
    )
}

@BindingAdapter("emojiResult")
fun bindEmojiResult(imageView: ImageView, gameResult: GameResult) {
    imageView.setImageResource(getSmileResId(gameResult))
}

private fun getPercentageOfRightAnswers(gameResult: GameResult): Int {
    return ((gameResult.countOfRightAnswers * 1.00 / gameResult.countOfAnswers) * 100).toInt()
}

private fun getSmileResId(gameResult: GameResult): Int {
    with(gameResult) {
        return if (winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enough: Boolean) {
    textView.setTextColor(getColorByState(textView.context, enough))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean) {
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}


interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}