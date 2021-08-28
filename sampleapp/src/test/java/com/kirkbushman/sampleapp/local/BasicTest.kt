package com.kirkbushman.sampleapp.local

import org.junit.Assert.*
import org.junit.Test

class BasicTest {

    @Test
    fun basicTest() {
        val sum = 2 + 2
        assertEquals("Assert basic sum of two numbers", 4, sum)
    }

    @Test
    fun basicTest2() {
        assertEquals("Assert basic replacing of string", "Hello World!".replace("World", "Universe"), "Hello Universe!")
    }
}
