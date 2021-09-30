package com.freeit.empathyquotient

import com.freeit.empathyquotient.data.QuestionDataSource
import org.junit.Assert
import org.junit.Test

class QuestionDataSourceTest {
    private val questionDataSource = QuestionDataSource.Mock()

    @Test
    fun testcase_all_one_answers() {
        val realScore = questionDataSource.quests().flatMap { it.rawAnswers() }.filter { it.id() == 0 }.map { it.sc() }.sum()
        val expected = 42
        Assert.assertEquals(expected, realScore)
    }

    @Test
    fun testcase_all_two_answers() {
        val realScore = questionDataSource.quests().flatMap { it.rawAnswers() }.filter { it.id() == 1 }.map { it.sc() }.sum()
        val expected = 21
        Assert.assertEquals(expected, realScore)
    }

    @Test
    fun testcase_all_three_answers() {
        val realScore = questionDataSource.quests().flatMap { it.rawAnswers() }.filter { it.id() == 2 }.map { it.sc() }.sum()
        val expected = 19
        Assert.assertEquals(expected, realScore)
    }

    @Test
    fun testcase_all_four_answers() {
        val realScore = questionDataSource.quests().flatMap { it.rawAnswers() }.filter { it.id() == 3 }.map { it.sc() }.sum()
        val expected = 38
        Assert.assertEquals(expected, realScore)
    }
}