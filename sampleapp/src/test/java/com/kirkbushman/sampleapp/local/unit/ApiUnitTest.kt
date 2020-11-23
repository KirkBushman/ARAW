package com.kirkbushman.sampleapp.local.unit

/*import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.utils.Endpoints
import com.kirkbushman.araw.utils.Utils
import com.kirkbushman.sampleapp.local.TestDataUtil
import com.kirkbushman.sampleapp.local.TestJsonDataUtil
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Call
import retrofit2.Retrofit

class ApiUnitTest {

    private val mockServer = MockWebServer()

    private lateinit var retrofit: Retrofit
    private lateinit var api: RedditApi

    @Test
    fun basicApiTestMe() {

        testModelJsonEquality(
            url = Endpoints.URL_ME,
            testData = TestDataUtil.meTestData,
            testJsonData = TestJsonDataUtil.meTestData,
            newCall = { api -> api.me(getFakeHashMap()) }
        )
    }

    /*@Test
    fun basicApiTestSubreddit() {

        val subredditData = TestDataUtil.getSubredditTestData()
        val subredditJsonData = TestJsonDataUtil.getSubredditJsonData(subredditData)

        testModelJsonEquality<EnvelopedSubreddit>(
            url = Endpoints.URL_SUBREDDIT,
            testData = subredditData,
            testJsonData = subredditJsonData,
            newCall = { api -> api.subreddit("whatever", rawJson = 0, getFakeHashMap()) }
        )
    }*/

    @Test
    fun basicApiTestRedditor() {

        val redditorData = TestDataUtil.getRedditorTestData()
        val redditorJsonData = TestJsonDataUtil.getRedditorJsonData(redditorData)

        testModelJsonEquality(
            url = Endpoints.URL_REDDITOR,
            testData = redditorData,
            testJsonData = redditorJsonData,
            newCall = { api -> api.redditor("whatever", rawJson = 0, getFakeHashMap()) }
        )
    }

    private fun <T> testModelJsonEquality(

        url: String,

        testData: T,
        testJsonData: String,

        newCall: (RedditApi) -> Call<T>
    ) {

        mockServer.start()

        val res = MockResponse().setBody(testJsonData)
        mockServer.enqueue(res)

        val baseUrl = mockServer.url(url.plus("/"))

        retrofit = Utils.buildRetrofit(baseUrl.toString(), logging = true)
        api = retrofit.create(RedditApi::class.java)

        val request = newCall(api)
        val response = request.execute()

        mockServer.takeRequest()
        assertEquals("Response should be the same", testData, response.body())

        mockServer.shutdown()
    }

    private fun getFakeHashMap(): HashMap<String, String> {

        return hashMapOf("Authorization" to "bearer TOKEN")
    }
}
*/
