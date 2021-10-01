package com.freeit.empathyquotient.data

data class Answer(
    private val number: Int,
    private val text: String,
    private val score: Int
) {
    fun id() = number - 1
    fun str() = "$number. $text"
    fun sc() = score
}