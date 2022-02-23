package com.freeit.empathyquotient.presentation.screens.intro

import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.LocalPrefsDataSource

class IntroQuestionDataSource(private val source: LocalPrefsDataSource) {

    private val questions = listOf(
        IntroQuestion(0, R.string.what_is_it_question, R.string.what_is_it_answer),
        IntroQuestion(1, R.string.who_developed_question, R.string.who_developed_answer),
        IntroQuestion(2, R.string.what_is_the_max_score_question, R.string.what_is_the_max_score_answer)
    )

    private var currentQuestion = 0

    init {
        currentQuestion = source.int(selectedIndexKey , 0)
    }

    fun question() : IntroQuestion {
        val question = questions[currentQuestion]
        return when (currentQuestion) {
            0 -> question.makeForwardVisible()
            questions.size - 1 -> question.makeBackVisible()
            else -> question.makeBackForwardVisible()
        }
    }

    fun next() {
        if (currentQuestion < questions.size - 1) {
            currentQuestion++
            source.saveInt(selectedIndexKey, currentQuestion)
        }
    }

    fun prev() {
        if (currentQuestion > 0) {
            currentQuestion--
            source.saveInt(selectedIndexKey, currentQuestion)
        }
    }

    fun select(questionIndex: Int) {
        if (questionIndex in questions.indices) {
            currentQuestion = questionIndex
            source.saveInt(selectedIndexKey, currentQuestion)
        }
    }

    companion object {
        private const val selectedIndexKey = "selected_index_key"
    }

}