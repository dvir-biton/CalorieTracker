package com.fylora.onboarding_presentation

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, sum(2, 2))
    }

    fun sum(x: Int, y: Int): Int = x + y
}