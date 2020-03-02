package com.kirkbushman.sampleapp.testing.integrationTesting

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.mixins.CommentData
import com.kirkbushman.araw.utils.toLinearList
import com.kirkbushman.araw.utils.treeIterable
import com.kirkbushman.araw.utils.treeIterator
import com.kirkbushman.sampleapp.testing.TestUtils
import junit.framework.Assert.assertTrue
import kotlinx.android.parcel.Parcelize
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

        val selectedSubmission = submissions
            .filter { it.numComments > 0 }
            .maxBy { it.numComments }
            ?: throw IllegalAccessError("No submission found!")

        val commentsFetcher = client!!.contributionsClient.comments(selectedSubmission.id)
        val comments = commentsFetcher.fetchNext()

        val linearList = comments.toLinearList()

        assertTrue("comment data should not have replies, once tranformed into a linearList",
            areRepliesBlank(linearList))

        assertTrue("unwrapped comment structure should be equal to list returned by .toLinearList()",
            compareCommentList(comments, linearList))

        val wrappedList = wrappedCommentModels(comments)

        assertTrue("once the models get wrapped, walking the tree there should be the same number of models and wrappers",
            compareModelNumber(wrappedList, linearList))
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
        comments.treeIterator().forEach {

            if (it.fullname != linearList[position].fullname) {
                return false
            }

            position++
        }

        return true
    }

    private fun wrappedCommentModels(comments: List<CommentData>): List<CommentData> {

        return comments.treeIterable().map {

            if(it is Comment) {
                TestComment(it)
            } else {
                it
            }
        }
    }

    private fun compareModelNumber(wrapped: List<CommentData>, linearList: List<CommentData>): Boolean {

        var wrappedNum = 0
        wrapped.treeIterator().forEach {

            if (it is TestComment) {
                wrappedNum++
            }
        }

        var linearNum = 0
        linearList.forEach {

            if (it is Comment) {
                linearNum++
            }
        }

        return wrappedNum == linearNum
    }

    @Parcelize
    class TestComment(private val comment: Comment): CommentData {

        override val id: String
            get() = comment.id

        override val fullname: String
            get() = comment.fullname

        override val depth: Int
            get() = comment.depth

        override val hasReplies: Boolean
            get() = comment.hasReplies

        override val replies: List<CommentData>?
            get() = comment.replies

        override val repliesSize: Int
            get() = comment.repliesSize
    }
}
