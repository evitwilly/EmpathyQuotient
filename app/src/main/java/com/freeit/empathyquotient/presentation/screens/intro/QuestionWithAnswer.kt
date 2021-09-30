package com.freeit.empathyquotient.presentation.screens.intro

data class QuestionWithAnswer(
    private val index: Int,
    private val question: String,
    private val answer: String,
    private val isLast: Boolean = false,
    private val isAnimation: Boolean = true
) {
    fun q() = question
    fun a() = answer
    fun i() = index
    fun isFirst() = index == 0
    fun isLast() = isLast
    fun isAnim() = isAnimation
}