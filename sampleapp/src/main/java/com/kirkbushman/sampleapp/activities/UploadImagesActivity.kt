package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.util.StorageUtil
import com.kirkbushman.sampleapp.util.DoAsync
import kotlinx.android.synthetic.main.activity_upload_images.*

class UploadImagesActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, UploadImagesActivity::class.java)
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
        setContentView(R.layout.activity_upload_images)

        bttn_upload.visibility = View.GONE
        bttn_upload.setOnClickListener {

            if (fileName != null &&
                mimeType != null &&
                fileContent != null &&
                bitmap != null
            ) {

                var mediaUrl: String? = null

                DoAsync(
                    doWork = {

                        mediaUrl = client?.contributionsClient?.uploadMedia(fileName!!, mimeType!!, fileContent!!)
                    },
                    onPost = {

                        if (mediaUrl != null) {

                            Toast.makeText(this, mediaUrl, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Error while uploading image!", Toast.LENGTH_SHORT).show()
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

            bttn_upload.visibility = View.VISIBLE
        }
    }
}
