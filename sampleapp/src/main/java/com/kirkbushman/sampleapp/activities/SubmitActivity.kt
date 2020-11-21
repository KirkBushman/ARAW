package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kirkbushman.araw.models.commons.SubmissionKind
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.util.DoAsync
import kotlinx.android.synthetic.main.activity_submit.*

class SubmitActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubmitActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        bttn_submit.setOnClickListener {

            DoAsync(
                doWork = {

                    val subredditName = edit_subreddit.text.toString().trim()
                    val title = edit_title.text.toString().trim()
                    val textOrLink = edit_text.text.toString().trim()
                    val kind = when {
                        bttn_radio_self.isChecked -> SubmissionKind.SELF
                        bttn_radio_link.isChecked -> SubmissionKind.LINK

                        else -> SubmissionKind.SELF
                    }

                    client?.subredditsClient?.submit(
                        subredditName = subredditName,
                        title = title,
                        kind = kind,
                        text = if (kind == SubmissionKind.SELF) textOrLink else "",
                        url = if (kind != SubmissionKind.SELF) textOrLink else "",
                        sendReplies = check_sendreplies.isChecked,
                        isNsfw = check_isnsfw.isChecked,
                        isSpoiler = check_isspoiler.isChecked
                    )
                }
            )
        }
    }
}
