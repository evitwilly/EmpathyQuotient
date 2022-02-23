package com.freeit.empathyquotient.presentation.screens.intro

import android.content.res.Resources
import android.view.View
import androidx.annotation.StringRes
import ru.freeit.noxml.extensions.visible

data class IntroQuestion(
    private val id: Int = 0,
    @StringRes private val question: Int,
    @StringRes private val answer: Int,
    private val backVisible: Boolean = false,
    private val forwardVisible: Boolean = true
) {
    fun questionString(resources: Resources) = resources.getString(question)
    fun answerString(resources: Resources) = resources.getString(answer)
    fun backButtonVisible(button: View) = button.visible(backVisible)
    fun forwardButtonVisible(button: View) = button.visible(forwardVisible)

    fun makeBackVisible() = copy(backVisible = true, forwardVisible = false)
    fun makeForwardVisible() = copy(forwardVisible = true, backVisible = false)
    fun makeBackForwardVisible() = copy(backVisible = true, forwardVisible = true)
    fun index() = id
}