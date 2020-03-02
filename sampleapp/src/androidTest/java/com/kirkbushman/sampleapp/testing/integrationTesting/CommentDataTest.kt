package com.kirkbushman.sampleapp.testing.integrationTesting

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.mixins.CommentData
import com.kirkbushman.araw.utils.toLinearList
import com.kirkbushman.sampleapp.testing.TestUtils
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommentDataTest {

    companion object {

        private val subreddits = arrayOf("relationships", "relationship_advice", "tifu", "worldnews")

        private const val LIMIT = 50
    }

    private val context by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext }
    private val manager by lazy { TestUtils.getAuthManager(context) }
    private val bearer by lazy { TestUtils.getTokenBearer(manager) }

    private var client: RedditClient? = null
    private val submissions = ArrayList<Submission>()

    @Before
    fun onPre() {
        client = RedditClient(bearer, true)

        val randomSub = subreddits.random()
        val fetcher = client!!.contributionsClient.submissions(randomSub, limit = LIMIT)
        submissions.addAll(fetcher.fetchNext())
    }

    @Test
    fun linearCommentsTest() {

        val selectedSubmission = submissions.filter { it.numComments > 0 }.random()
        val commentsFetcher = client!!.contributionsClient.comments(selectedSubmission.id)
        val comments = commentsFetcher.fetchNext()

        val linearList = comments.toLinearList()

        assertTrue("comment data should not have replies, once tranformed into a linearList",
            areRepliesBlank(linearList))

        assertTrue("unwrapped comment structure should be equal to list returned by .toLinearList()",
            compareCommentList(comments, linearList))
    }

    private fun areRepliesBlank(list: List<CommentData>): Boolean {
        list.forEach {

            if (it.hasReplies) {
                return false
            }
        }

        return true
    }

    private fun compareCommentList(comments: List<CommentData>, linearList: List<CommentData>): Boolean {

        var position = 0
        comments.iterator().forEach {

            if (it.fullname != linearList[position].fullname) {
                return false
            }

            position++
        }

        return true
    }
}
