package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.airbnb.epoxy.EpoxyRecyclerView
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseControllerActivity2
import com.kirkbushman.sampleapp.controllers.BaseCallback
import com.kirkbushman.sampleapp.controllers.BaseController
import com.kirkbushman.sampleapp.controllers.TrophiesController
import kotlinx.android.synthetic.main.activity_self_trophies.*

class SelfTrophiesActivity : BaseControllerActivity2<Trophy>(R.layout.activity_self_trophies) {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SelfTrophiesActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val actionBar: Toolbar
        get() = toolbar

    override val recyclerView: EpoxyRecyclerView
        get() = list

    override val controller: BaseController<Trophy, BaseCallback>
        get() = TrophiesController()

    override fun fetchItem(client: RedditClient?): Collection<Trophy>? {

        return client?.accountsClient?.myTrophies()
    }
}
