package com.freeit.empathyquotient.data

import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.core.content.ContextCompat
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.presentation.view.other.AnswerView

data class Question(
    private val number: Int = 0,
    private val text: String,
    private val answers: List<Answer>
) {

    fun id() = number - 1

    fun str(ctx: Context) = SpannableString("$number. $text").apply {
        setSpan(RelativeSizeSpan(1.2f), 0, number.toString().length, 0)
        setSpan(ForegroundColorSpan(ContextCompat.getColor(ctx, R.color.yellow_500)), 0, number.toString().length, 0)
    }

    fun rawAnswers() = answers

    fun answers(ctx: Context, selectedId: Int = -1) = answers.map { answer ->
        AnswerView(ctx, isInitialChecked = answer.id() == selectedId).apply {
            setText(answer.str())
        }
    }

}