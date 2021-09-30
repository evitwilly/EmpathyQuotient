package com.freeit.empathyquotient.data

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.presentation.view.AnswerView

data class Answer(
    private val number: Int,
    private val text: String,
    private val score: Int
) {
    fun id() = number - 1
    fun str() = "$number. $text"
    fun sc() = score
}

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

interface QuestionDataSource {
    fun quests() : List<Question>
    fun hasNext(questionId: Int) : Boolean
    fun questById(id: Int) : Question
    
    class Mock : QuestionDataSource {

        private val questions = listOf(
            Question(1, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(2, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(3, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(4, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(5, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(6, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(7, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(8, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(9, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(10, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(11, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(12, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(13, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(14, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(15, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(16, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(17, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(18, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(19, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(20, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(21, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(22, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(23, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(24, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(25, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(26, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(27, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(28, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(29, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(30, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(31, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(32, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(33, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(34, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(35, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(36, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(37, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(38, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(39, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(40, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(41, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(42, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(43, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(44, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(45, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(46, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(47, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(48, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(49, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(50, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 1),
                Answer(4, "", 2)
            )),
            Question(51, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(52, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(53, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(54, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(55, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(56, "", listOf(
                Answer(1, "", 0),
                Answer(2, "", 0),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(57, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(58, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(59, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            )),
            Question(60, "", listOf(
                Answer(1, "", 2),
                Answer(2, "", 1),
                Answer(3, "", 0),
                Answer(4, "", 0)
            ))
        )

        override fun quests() = questions
        override fun hasNext(questionId: Int): Boolean {
            val nextQuestionId = questionId + 1
            return nextQuestionId >= 0 && nextQuestionId <= questions.size.minus(1)
        }

        override fun questById(id: Int) = questions.getOrNull(id) ?: questions.first()
    }
    

    class Base(private val ctx: Context) : QuestionDataSource {

        private val questions = listOf(
            Question(1, ctx.getString(R.string.question_1), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(2, ctx.getString(R.string.question_2), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(3, ctx.getString(R.string.question_3), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(4, ctx.getString(R.string.question_4), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(5, ctx.getString(R.string.question_5), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(6, ctx.getString(R.string.question_6), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(7, ctx.getString(R.string.question_7), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(8, ctx.getString(R.string.question_8), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(9, ctx.getString(R.string.question_9), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(10, ctx.getString(R.string.question_10), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(11, ctx.getString(R.string.question_11), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(12, ctx.getString(R.string.question_12), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(13, ctx.getString(R.string.question_13), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(14, ctx.getString(R.string.question_14), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(15, ctx.getString(R.string.question_15), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(16, ctx.getString(R.string.question_16), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(17, ctx.getString(R.string.question_17), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(18, ctx.getString(R.string.question_18), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(19, ctx.getString(R.string.question_19), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(20, ctx.getString(R.string.question_20), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(21, ctx.getString(R.string.question_21), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(22, ctx.getString(R.string.question_22), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(23, ctx.getString(R.string.question_23), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(24, ctx.getString(R.string.question_24), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(25, ctx.getString(R.string.question_25), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(26, ctx.getString(R.string.question_26), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(27, ctx.getString(R.string.question_27), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(28, ctx.getString(R.string.question_28), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(29, ctx.getString(R.string.question_29), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(30, ctx.getString(R.string.question_30), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(31, ctx.getString(R.string.question_31), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(32, ctx.getString(R.string.question_32), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(33, ctx.getString(R.string.question_33), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(34, ctx.getString(R.string.question_34), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(35, ctx.getString(R.string.question_35), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(36, ctx.getString(R.string.question_36), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(37, ctx.getString(R.string.question_37), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(38, ctx.getString(R.string.question_38), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(39, ctx.getString(R.string.question_39), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(40, ctx.getString(R.string.question_40), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(41, ctx.getString(R.string.question_41), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(42, ctx.getString(R.string.question_42), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(43, ctx.getString(R.string.question_43), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(44, ctx.getString(R.string.question_44), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(45, ctx.getString(R.string.question_45), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(46, ctx.getString(R.string.question_46), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(47, ctx.getString(R.string.question_47), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(48, ctx.getString(R.string.question_48), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(49, ctx.getString(R.string.question_49), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(50, ctx.getString(R.string.question_50), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 1),
                Answer(4, ctx.getString(R.string.strongly_disagree), 2)
            )),
            Question(51, ctx.getString(R.string.question_51), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(52, ctx.getString(R.string.question_52), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(53, ctx.getString(R.string.question_53), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(54, ctx.getString(R.string.question_54), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(55, ctx.getString(R.string.question_55), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(56, ctx.getString(R.string.question_56), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 0),
                Answer(2, ctx.getString(R.string.slightly_agree), 0),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(57, ctx.getString(R.string.question_57), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(58, ctx.getString(R.string.question_58), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(59, ctx.getString(R.string.question_59), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            )),
            Question(60, ctx.getString(R.string.question_60), listOf(
                Answer(1, ctx.getString(R.string.strongly_agree), 2),
                Answer(2, ctx.getString(R.string.slightly_agree), 1),
                Answer(3, ctx.getString(R.string.slightly_disagree), 0),
                Answer(4, ctx.getString(R.string.strongly_disagree), 0)
            ))
        )

        override fun quests() = questions
        override fun hasNext(questionId: Int): Boolean {
            val nextQuestionId = questionId + 1
            return nextQuestionId >= 0 && nextQuestionId <= questions.size.minus(1)
        }

        override fun questById(id: Int) = questions.getOrNull(id) ?: questions.first()
    }

}