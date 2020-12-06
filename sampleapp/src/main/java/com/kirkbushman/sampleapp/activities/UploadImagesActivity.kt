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
import com.kirkbushman.sampleapp.databinding.ActivityUploadImagesBinding
import com.kirkbushman.sampleapp.util.StorageUtil
import com.kirkbushman.sampleapp.util.DoAsync

class UploadImagesActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, UploadImagesActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private lateinit var binding: ActivityUploadImagesBinding

    private var fileName: String? = null
    private var mimeType: String? = null
    private var fileContent: ByteArray? = null
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadImagesBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_upload_images)

        binding.bttnUpload.visibility = View.GONE
        binding.bttnUpload.setOnClickListener {

            val hasFile = fileName != null &&
                mimeType != null &&
                fileContent != null &&
                bitmap != null

            if (hasFile) {

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

            binding.bttnUpload.visibility = View.VISIBLE
        }
    }
}
