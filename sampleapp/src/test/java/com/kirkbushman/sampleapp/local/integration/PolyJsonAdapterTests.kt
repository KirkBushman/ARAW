package com.kirkbushman.sampleapp.local.integration

import com.kirkbushman.araw.adapters.PolyJsonAdapterFactory
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PolyJsonAdapterTests {

    companion object {

        private const val TAG_NOISE = "noise"
        private const val TAG_IS_HAIRY = "is_hairy"
        private const val TAG_IS_SWIMMER = "is_good_swimmer"

        private const val VAL_CAT_NOISE = "miaooooo"
        private const val VAL_DOLPHIN_NOISE = "yeeeeeeee"

        private const val JSON_CAT = "{\"$TAG_NOISE\":\"$VAL_CAT_NOISE\",\"$TAG_IS_HAIRY\":true}"
        private const val JSON_DOLPHIN = "{\"$TAG_NOISE\":\"$VAL_DOLPHIN_NOISE\",\"$TAG_IS_SWIMMER\":true}"
    }

    @Test
    fun testRetrievedCatAdapter() {

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Cat::class.java)

        assertTrue(adapter is JsonAdapter<Cat>)
    }

    @Test
    fun testRetrievedDolphinAdapter() {

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Dolphin::class.java)

        assertTrue(adapter is JsonAdapter<Dolphin>)
    }

    @Test
    fun parseByCommonParams() {

        val moshi = Moshi.Builder()
            .add(
                PolyJsonAdapterFactory(
                    baseType = Animal::class.java,
                    possibleTypes = arrayOf(Cat::class.java, Dolphin::class.java),
                    selectType = { label, _ ->

                        when(label) {
                            TAG_IS_HAIRY -> Cat::class.java
                            TAG_IS_SWIMMER -> Dolphin::class.java
                            else -> null
                        }
                    }
                )
            ).build()

        val adapter = moshi.adapter(Animal::class.java)

        val cat = adapter.fromJson(JSON_CAT)
        val dolphin = adapter.fromJson(JSON_DOLPHIN)

        assertNotNull(cat)
        assertNotNull(dolphin)

        assertTrue(cat is Cat)
        assertTrue(dolphin is Dolphin)
    }

    @Test
    fun parseByCommonParamsToJson() {

        val moshi = Moshi.Builder()
            .add(
                PolyJsonAdapterFactory(
                    baseType = Animal::class.java,
                    possibleTypes = arrayOf(Cat::class.java, Dolphin::class.java),
                    selectType = { label, _ ->

                        when(label) {
                            TAG_IS_HAIRY -> Cat::class.java
                            TAG_IS_SWIMMER -> Dolphin::class.java
                            else -> null
                        }
                    }
                )
            ).build()

        val adapter = moshi.adapter(Animal::class.java)

        val cat = Cat(VAL_CAT_NOISE, true)
        val catJson = adapter.toJson(cat)

        assertNotNull(catJson)
        assertEquals(catJson, JSON_CAT)

        val dolphin = Dolphin(VAL_DOLPHIN_NOISE, true)
        val dolphinJson = adapter.toJson(dolphin)

        assertNotNull(dolphinJson)
        assertEquals(dolphinJson, JSON_DOLPHIN)
    }

    @Test
    fun testByUniqueParams() {

        val moshi = Moshi.Builder()
            .add(
                PolyJsonAdapterFactory(
                    baseType = Animal::class.java,
                    possibleTypes = arrayOf(Cat::class.java, Dolphin::class.java),
                    selectType = { label, value ->

                        when {
                            label == TAG_NOISE && value == VAL_CAT_NOISE -> Cat::class.java
                            label == TAG_NOISE && value == VAL_DOLPHIN_NOISE -> Dolphin::class.java
                            else -> null
                        }
                    }
                )
            ).build()

        val adapter = moshi.adapter(Animal::class.java)

        val cat = adapter.fromJson(JSON_CAT)
        val dolphin = adapter.fromJson(JSON_DOLPHIN)

        assertNotNull(cat)
        assertNotNull(dolphin)

        assertTrue(cat is Cat)
        assertTrue(dolphin is Dolphin)
    }

    @Test
    fun testByUniqueParamsToJson() {

        val moshi = Moshi.Builder()
            .add(
                PolyJsonAdapterFactory(
                    baseType = Animal::class.java,
                    possibleTypes = arrayOf(Cat::class.java, Dolphin::class.java),
                    selectType = { label, value ->

                        when {
                            label == TAG_NOISE && value == VAL_CAT_NOISE -> Cat::class.java
                            label == TAG_NOISE && value == VAL_DOLPHIN_NOISE -> Dolphin::class.java
                            else -> null
                        }
                    }
                )
            ).build()

        val adapter = moshi.adapter(Animal::class.java)

        val cat = Cat(VAL_CAT_NOISE, true)
        val catJson = adapter.toJson(cat)

        assertNotNull(catJson)
        assertEquals(catJson, JSON_CAT)

        val dolphin = Dolphin(VAL_DOLPHIN_NOISE, true)
        val dolphinJson = adapter.toJson(dolphin)

        assertNotNull(dolphinJson)
        assertEquals(dolphinJson, JSON_DOLPHIN)
    }
}

interface Animal {

    val noise: String
}

@JsonClass(generateAdapter = true)
data class Cat(
    @Json(name = "noise")
    override val noise: String,
    @Json(name = "is_hairy")
    val isHairy: Boolean
) : Animal

@JsonClass(generateAdapter = true)
data class Dolphin(
    @Json(name = "noise")
    override val noise: String,
    @Json(name = "is_good_swimmer")
    val isGoodSwimmer: Boolean
) : Animal
