package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kirkbushman.araw.models.commons.SubmissionKind
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.databinding.ActivitySubmitBinding
import com.kirkbushman.sampleapp.util.DoAsync

class SubmitActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubmitActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private lateinit var binding: ActivitySubmitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubmitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        binding.bttnSubmit.setOnClickListener {

            DoAsync(
                doWork = {

                    val subredditName = binding.editSubreddit.text.toString().trim()
                    val title = binding.editTitle.text.toString().trim()
                    val textOrLink = binding.editText.text.toString().trim()
                    val kind = when {
                        binding.bttnRadioSelf.isChecked -> SubmissionKind.SELF
                        binding.bttnRadioLink.isChecked -> SubmissionKind.LINK

                        else -> SubmissionKind.SELF
                    }

                    client?.subredditsClient?.submit(
                        subredditName = subredditName,
                        title = title,
                        kind = kind,
                        text = if (kind == SubmissionKind.SELF) textOrLink else "",
                        url = if (kind != SubmissionKind.SELF) textOrLink else "",
                        sendReplies = binding.checkSendreplies.isChecked,
                        isNsfw = binding.checkIsnsfw.isChecked,
                        isSpoiler = binding.checkIsspoiler.isChecked
                    )
                }
            )
        }
    }
}
