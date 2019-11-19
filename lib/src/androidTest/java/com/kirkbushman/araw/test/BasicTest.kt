package com.kirkbushman.araw.test

import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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
