package com.kirkbushman.sampleapp.testing

import org.junit.Assert.*
import org.junit.Test

class BasicTest {

    @Test
    fun basicTest() {
        assertEquals("Assert basic sum of two numbers", 4, 2 + 2)
    }

    @Test
    fun basicTest2() {
        assertEquals("Assert basic replacing of string", "Hello World!".replace("World", "Universe"), "Hello Universe!")
    }
}
