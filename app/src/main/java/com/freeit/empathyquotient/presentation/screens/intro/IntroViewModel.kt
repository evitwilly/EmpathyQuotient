package com.freeit.empathyquotient.presentation.screens.intro

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.LocalPrefsDataSource

class IntroViewModel(
    private val ctx: Context,
    private val localPrefsDataSource: LocalPrefsDataSource
) : ViewModel() {

    private val infoQuestions = listOf(
        QuestionWithAnswer(
            0,
            ctx.getString(R.string.what_is_it_question),
            ctx.getString(R.string.what_is_it_answer)
        ),
        QuestionWithAnswer(
            1,
            ctx.getString(R.string.who_developed_question),
            ctx.getString(R.string.who_developed_answer)
        ),
        QuestionWithAnswer(
            2,
            ctx.getString(R.string.what_is_the_max_score_question),
            ctx.getString(R.string.what_is_the_max_score_answer)
        ),
    )

    private val selectedIndexKey = "selected_index_key"

    private var currentQuestionIndex = 0

    private val _currentQuestion = MutableLiveData<QuestionWithAnswer>()
    val selectedQuestion: LiveData<QuestionWithAnswer>
        get() = _currentQuestion

    init {
        val index = localPrefsDataSource.int(selectedIndexKey , 0)
        _currentQuestion.value = infoQuestions[index].copy(isLast = index == infoQuestions.size - 1, isAnimation = false)
        currentQuestionIndex = index
    }

    fun next() {
        if (currentQuestionIndex < infoQuestions.size - 1) {
            currentQuestionIndex++
            val isLast = currentQuestionIndex == infoQuestions.size - 1
            _currentQuestion.value = infoQuestions[currentQuestionIndex].copy(isLast = isLast)
            localPrefsDataSource.save(selectedIndexKey, currentQuestionIndex)
        }
    }

    fun select(i: Int) {
        if (i > -1 && i < infoQuestions.size) {
            currentQuestionIndex = i
            _currentQuestion.value = infoQuestions[currentQuestionIndex]
            localPrefsDataSource.save(selectedIndexKey, currentQuestionIndex)
        }
    }

    fun prev() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--
            _currentQuestion.value = infoQuestions[currentQuestionIndex]
            localPrefsDataSource.save(selectedIndexKey, currentQuestionIndex)
        }
    }


}