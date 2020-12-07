package com.kirkbushman.sampleapp.di

import android.content.Context
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.helpers.AuthAppHelper
import com.kirkbushman.araw.helpers.AuthUserlessHelper
import com.kirkbushman.sampleapp.TestCredentials
import com.kirkbushman.sampleapp.utils.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Provider {

    @Provides
    @Singleton
    fun provideTestCreds(@ApplicationContext context: Context): TestCredentials {

        return Utils.loadCredentials(context)
    }

    @Provides
    @Singleton
    fun provideAppAuthHelper(

        @ApplicationContext context: Context,
        creds: TestCredentials
    ): AuthAppHelper {

        return AuthAppHelper(
            context = context,
            clientId = creds.clientId,
            redirectUrl = creds.redirectUrl,
            scopes = creds.scopes.toTypedArray(),
            logging = true
        )
    }

    @Provides
    @Singleton
    fun provideAuthUserlessHelper(

        @ApplicationContext context: Context,
        creds: TestCredentials
    ): AuthUserlessHelper {

        return AuthUserlessHelper(
            context = context,
            clientId = creds.clientId,
            logging = true
        )
    }

    @Volatile
    private var mRedditClient: RedditClient? = null

    @JvmStatic
    @Synchronized
    fun setRedditClient(client: RedditClient) {
        return synchronized(this) {

            if (mRedditClient == null) {
                mRedditClient = client
            }
        }
    }

    @Provides
    @Singleton
    fun provideRedditClient(

        appAuth: AuthAppHelper,
        userlessAuth: AuthUserlessHelper
    ): RedditClient {

        if (mRedditClient == null) {

            // check if there is a registered token
            // with username and password
            if (appAuth.hasSavedBearer()) {
                mRedditClient = appAuth.getSavedRedditClient()
            }

            // if there is no token, verify if there is a userless token
            if (userlessAuth.hasSavedBearer()) {
                mRedditClient = userlessAuth.getSavedRedditClient()
            }
        }

        return mRedditClient!!
    }
}
