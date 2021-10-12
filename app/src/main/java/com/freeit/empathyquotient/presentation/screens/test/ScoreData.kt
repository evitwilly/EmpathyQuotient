package com.freeit.empathyquotient.presentation.screens.test

import com.freeit.empathyquotient.core.LocalPrefsDataSource

class ScoreData(private val appPrefs: LocalPrefsDataSource) {
    companion object {
        private const val scoreKey = "score_key"
        private val defaultValue = List(60) { 0 }.joinToString(",")
    }

    fun select(questionId: Int, score: Int) {
        val lastValue = appPrefs.str(scoreKey, defaultValue)
        val scores = lastValue.split(",").map { it.toInt() }.toMutableList()
        scores[questionId] = score
        appPrefs.saveStr(scoreKey, scores.joinToString(","))
    }

    fun score() : String {
        val lastValue = appPrefs.str(scoreKey, defaultValue)
        return lastValue.split(",").map { it.toInt() }.sum().toString()
    }
}