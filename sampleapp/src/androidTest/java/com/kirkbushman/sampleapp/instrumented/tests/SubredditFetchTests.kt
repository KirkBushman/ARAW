package com.kirkbushman.sampleapp.instrumented.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.instrumented.TestUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// Here I test weird subreddits that have caused me problems.
@RunWith(AndroidJUnit4::class)
class SubredditFetchTests {

    companion object {

        // very weird subreddits
        private val subreddits = arrayOf(
            "AquaticAsFuck",
            "bioinformatics",
            "biology",
            "mtg",
            "microbiology",
            "archlinux",
            "Norway",
            "biotech",
            "puppy",
            "ocean",
            "oceans",
            "redpandas",
            "oceanography",
            "GirlGamers",
            "Cetacea",
            "marinebiology",
            "molecularbiology",
            "AskBiology",
            "KarmaForBrats",
            "unixporn",
            "duolingo",
            "i3wm",
            "norsk",
            "shitduolingosays",
            "holdmycatnip",
            "MarineBiologyNews",
            "babyelephantgifs",
            "redpanda_gifs",
            "BabyCorgis",
            "holdmybeaker",
            "spacemacs",
            "MarineBiologyGifs",
            "happycowgifs",
            "AnimalsBeingConfused",
            "CustomKeyboards",
            "FourSouls",
            "BiologicalSeas",
            "TreesSuckingOnThings",
            "birdswitharms",
            "peoplewithbirdheads",
            "fifthworldproblems",
            "unstirredpaint",
            "chairsunderwater",
            "boottoobig",
            "myevilplan",
            "StannisTheMantis",
            "BreadStapledToTrees",
            "SubredditSimulator",
            "monkslookingatbeer",
            "Ooer"
        )
    }

    private val context by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext }
    private val manager by lazy { TestUtils.getAuthManager(context) }
    private val bearer by lazy { TestUtils.getTokenBearer(manager) }

    private var client: RedditClient? = null

    @Before
    fun onPre() {
        client = RedditClient(bearer, true)
    }

    @Test
    fun subredditFetchingTest() {

        var exception: Exception? = null

        try {

            subreddits.forEach {

                val subreddit = client?.subredditsClient?.subreddit(it)
                Assert.assertNotEquals(
                    "Assert that the subreddit /r/$it is not null",
                    null,
                    subreddit
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            Assert.assertNull("Check there are no exceptions thrown", exception)
        }
    }
}
