package com.kirkbushman.sampleapp.instrumented.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.MoreComments
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.base.CommentData
import com.kirkbushman.araw.utils.toLinearList
import com.kirkbushman.araw.utils.treeIterable
import com.kirkbushman.araw.utils.treeIterator
import com.kirkbushman.sampleapp.instrumented.TestUtils
import kotlinx.parcelize.Parcelize
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommentDataTest {

    companion object {

        private val subreddits = arrayOf("relationships", "relationship_advice", "tifu", "worldnews")

        private const val LIMIT = 250L
    }

    private val context by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext }
    private val manager by lazy { TestUtils.getAuthManager(context) }
    private val bearer by lazy { TestUtils.getTokenBearer(manager) }

    private var client: RedditClient? = null
    private val submissions = ArrayList<Submission>()

    @Before
    fun onPre() {
        client = RedditClient(bearer, true, true)

        val randomSub = subreddits.random()
        val fetcher = client!!.contributionsClient.createSubmissionsFetcher(subreddit = randomSub, limit = LIMIT)

        val addendum = fetcher.fetchNext()
        submissions.addAll(addendum ?: emptyList())
    }

    @Test
    fun linearCommentsTest() {

        val selectedSubmission = submissions
            .filter { it.numComments > 0 }
            .maxByOrNull { it.numComments }
            ?: throw IllegalAccessError("No submission found!")

        val commentsFetcher = client!!.contributionsClient.createCommentsFetcher(selectedSubmission.id)
        val comments = commentsFetcher.fetchNext() ?: emptyList()

        val linearList = comments.toLinearList()

        assertTrue(
            "comment data should not have replies, once tranformed into a linearList",
            areRepliesBlank(linearList)
        )

        assertTrue(
            "unwrapped comment structure should be equal to list returned by .toLinearList()",
            compareCommentList(comments, linearList)
        )

        val wrappedComm = wrappedCommentModels(comments)

        assertTrue(
            "once the models get wrapped, walking the tree there should be the same number of models and wrappers",
            compareModelNumber(wrappedComm, comments)
        )

        val wrappedList = wrappedLinearCommentModels(comments)

        assertTrue(
            "once the models get wrapped, walking the list there should be the same number of models and wrappers",
            compareModelLinearNumber(wrappedList, linearList)
        )
    }

    private fun areRepliesBlank(list: List<CommentData>): Boolean {
        list.forEach {

            if (it.hasReplies) {
                return false
            }
        }

        return true
    }

    private fun compareCommentList(comments: List<CommentData>?, linearList: List<CommentData>): Boolean {

        var position = 0
        comments?.treeIterator()?.forEach {

            if (it.fullname != linearList[position].fullname) {
                return false
            }

            position++
        }

        return true
    }

    private fun wrappedCommentModels(comments: List<CommentData>?): List<CommentData> {

        val childMap = HashMap<String, CommentData>()
        val newComm = ArrayList<CommentData>()

        // wrap the children
        comments?.treeIterator()?.forEach {

            if (it.depth == 0) {
                val copyComm = deepCopy(it)
                newComm.add(copyComm)

                if (it.hasReplies) {
                    it.replies?.forEach { r ->

                        childMap[r.fullname] = copyComm
                    }
                }
            } else {
                val copyComm = deepCopy(it)
                val parent = childMap[copyComm.fullname]

                childMap.remove(copyComm.fullname)

                if (parent != null) {
                    addToNode(parent, copyComm)
                } else {
                    throw IllegalStateException("Cannot find parent node to add child!")
                }

                if (it.hasReplies) {
                    it.replies?.forEach { r ->

                        childMap[r.fullname] = copyComm
                    }
                }
            }
        }

        return newComm
    }

    private fun wrappedLinearCommentModels(comments: List<CommentData>): List<CommentData> {

        return comments.toList().treeIterable().map {

            if (it is Comment) {
                TestComment(it.copy())
            } else {
                deepCopy(it)
            }
        }
    }

    /**
     * Once wrapped there should be the same amount of items, in the same place, with the same id
     */
    private fun compareModelNumber(wrapped: List<CommentData>, comment: List<CommentData>): Boolean {

        val wrappedNames = ArrayList<String>()
        val commentNames = ArrayList<String>()

        var wrappedNum = 0
        wrapped.treeIterator().forEach {

            if (it is TestComment) {
                wrappedNum++
            }

            wrappedNames.add(it.fullname)
        }

        var commentsNum = 0
        comment.treeIterator().forEach {

            if (it is Comment) {
                commentsNum++
            }

            commentNames.add(it.fullname)
        }

        return wrappedNum == commentsNum && wrappedNames.toTypedArray().contentEquals(commentNames.toTypedArray())
    }

    /**
     * Walking the tree and running through the list one should have the same amounts of items
     */
    private fun compareModelLinearNumber(wrapped: List<CommentData>, linearList: List<CommentData>): Boolean {

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

    /**
     * Copy and wrap single CommentData
     */
    private fun deepCopy(item: CommentData): CommentData {
        return when (item) {
            is Comment -> {
                val newItem = TestComment(item.copy())
                newItem.replies = null

                newItem
            }
            is MoreComments -> item.copy()
            else -> item
        }
    }

    private fun addToNode(parent: CommentData, child: CommentData) {
        when (parent) {
            is Comment -> {
                if (parent.replies != null) {
                    val newRep = parent.replies!!.toMutableList()
                    newRep.add(child)

                    parent.replies = newRep
                } else {
                    parent.replies = listOf(child)
                }
            }
            is TestComment -> {
                if (parent.replies != null) {
                    val newRep = parent.replies!!.toMutableList()
                    newRep.add(child)

                    parent.replies = newRep
                } else {
                    parent.replies = listOf(child)
                }
            }
        }
    }

    /**
     * Wrapper class to Comment, implementing CommentData,
     * we are using this class to test the possibility to use treeIterator()
     * on CommentData lists to add additional methods on the items while in the tree structure
     */
    @Parcelize
    data class TestComment(private val comment: Comment) : CommentData {

        override val id: String
            get() = comment.id

        override val fullname: String
            get() = comment.fullname

        override val depth: Int
            get() = comment.depth

        override val parentFullname: String
            get() = comment.parentFullname

        override val hasReplies: Boolean
            get() = comment.hasReplies

        override var replies: List<CommentData>?
            get() = comment.replies
            set(value) {
                comment.replies = value
            }

        override val repliesSize: Int
            get() = comment.repliesSize
    }
}
