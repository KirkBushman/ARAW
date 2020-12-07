package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.commons.SubmissionKind
import com.kirkbushman.sampleapp.databinding.ActivitySubmitMediaBinding
import com.kirkbushman.sampleapp.utils.StorageUtil
import com.kirkbushman.sampleapp.utils.DoAsync
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SubmitMediaActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubmitMediaActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var client: RedditClient

    private lateinit var binding: ActivitySubmitMediaBinding

    private var fileName: String? = null
    private var mimeType: String? = null
    private var fileContent: ByteArray? = null
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubmitMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bttnSubmit.visibility = View.GONE
        binding.bttnSubmit.setOnClickListener {

            if (fileName != null &&
                mimeType != null &&
                fileContent != null
            ) {

                DoAsync(
                    doWork = {

                        val mediaUrl = client.contributionsClient.uploadMedia(fileName!!, mimeType!!, fileContent!!)
                        if (mediaUrl != null) {

                            val subreddit = binding.editSubreddit.text.trim().toString()
                            val title = binding.editTitle.text.trim().toString()

                            val kind = when {
                                mimeType!!.contains("image") -> SubmissionKind.IMAGE
                                mimeType!!.contains("video") -> SubmissionKind.VIDEO

                                else -> SubmissionKind.LINK
                            }

                            client.subredditsClient.submit(
                                subredditName = subreddit,
                                resubmit = true,
                                sendReplies = true,
                                kind = kind,
                                url = mediaUrl,
                                title = title,
                                isNsfw = false,
                                isSpoiler = false,
                                submitType = "subreddit",
                                isOriginalContent = false,
                                validateOnSubmit = true,
                                showErrorList = true
                            )
                        } else {
                            Log.i("Submitting Media", "Could not upload media, returned null url!")
                        }
                    }
                )
            }
        }

        binding.bttnLoadImage.setOnClickListener {

            StorageUtil.openMediaChooser(this)
        }

        binding.imageFilename.visibility = View.GONE
        binding.imageToUpload.visibility = View.GONE

        binding.imageToUpload.setImageDrawable(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        StorageUtil.handleOpenMediaChooserResult(

            activity = this,
            requestCode = requestCode,
            resultCode = resultCode,
            resultIntent = data
        ) { fileName, mimeType, fileContent, bitmap ->

            this.fileName = fileName
            this.mimeType = mimeType
            this.fileContent = fileContent
            this.bitmap = bitmap

            binding.imageFilename.visibility = View.VISIBLE
            binding.imageToUpload.visibility = View.VISIBLE

            val imageNameText = "name: $fileName, mime-type: $mimeType"
            binding.imageFilename.text = imageNameText

            binding.imageToUpload.setImageBitmap(bitmap)

            binding.bttnSubmit.visibility = View.VISIBLE
        }
    }
}
