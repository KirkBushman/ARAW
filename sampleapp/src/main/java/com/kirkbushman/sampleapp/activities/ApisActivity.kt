package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.R
import kotlinx.android.synthetic.main.activity_apis.*

class ApisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apis)

        bttn_me.setOnClickListener {

            ApiDetailActivity.startApiMe(this)
        }

        bttn_my_blocked.setOnClickListener {

            ApiDetailActivity.startApiMyBlocked(this)
        }

        bttn_my_friends.setOnClickListener {

            ApiDetailActivity.startApiMyFriends(this)
        }

        bttn_my_karma.setOnClickListener {

            ApiDetailActivity.startApiMyKarma(this)
        }

        bttn_my_prefs.setOnClickListener {

            ApiDetailActivity.startApiMyPrefs(this)
        }

        bttn_my_trophies.setOnClickListener {

            ApiDetailActivity.startApiMyTrophies(this)
        }
    }
}
