package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.commons.SubmissionKind
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.util.StorageUtil
import com.kirkbushman.sampleapp.util.DoAsync
import kotlinx.android.synthetic.main.activity_submit_media.*

class SubmitMediaActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubmitMediaActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private var fileName: String? = null
    private var mimeType: String? = null
    private var fileContent: ByteArray? = null
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_media)

        bttn_submit.visibility = View.GONE
        bttn_submit.setOnClickListener {

            if (fileName != null &&
                mimeType != null &&
                fileContent != null
            ) {

                DoAsync(
                    doWork = {

                        val mediaUrl = client?.contributionsClient?.uploadMedia(fileName!!, mimeType!!, fileContent!!)
                        if (mediaUrl != null) {

                            val subreddit = edit_subreddit.text.trim().toString()
                            val title = edit_title.text.trim().toString()

                            val kind = when {
                                mimeType!!.contains("image") -> SubmissionKind.IMAGE
                                mimeType!!.contains("video") -> SubmissionKind.VIDEO

                                else -> SubmissionKind.LINK
                            }

                            client?.subredditsClient?.submit(
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

        bttn_load_image.setOnClickListener {

            StorageUtil.openMediaChooser(this)
        }

        image_filename.visibility = View.GONE
        image_to_upload.visibility = View.GONE

        image_to_upload.setImageDrawable(null)
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

            image_filename.visibility = View.VISIBLE
            image_to_upload.visibility = View.VISIBLE

            val imageNameText = "name: $fileName, mime-type: $mimeType"
            image_filename.text = imageNameText

            image_to_upload.setImageBitmap(bitmap)

            bttn_submit.visibility = View.VISIBLE
        }
    }
}
