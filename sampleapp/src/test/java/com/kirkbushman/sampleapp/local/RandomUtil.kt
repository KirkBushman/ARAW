package com.kirkbushman.sampleapp.local

import kotlin.random.Random

object RandomUtil {

    private const val RANDOM_STR_LENGTH = 10
    private const val RANDOM_INT_LIMIT = 1000
    private const val RANDOM_LNG_LIMIT = 10000L

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun randomString(): String {

        return (1..RANDOM_STR_LENGTH)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun randomInt(): Int {

        return Random.nextInt(0, RANDOM_INT_LIMIT)
    }

    fun randomLong(): Long {

        return Random.nextLong(0L, RANDOM_LNG_LIMIT)
    }

    fun randomBool(): Boolean {

        return Random.nextBoolean()
    }
}
