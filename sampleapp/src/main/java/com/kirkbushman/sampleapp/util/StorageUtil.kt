package com.kirkbushman.sampleapp.util

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.provider.OpenableColumns
import java.io.InputStream

object StorageUtil {

    private const val CODE_CHOOSE_PIC = 1399

    fun openMediaChooser(activity: Activity) {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        intent.action = Intent.ACTION_GET_CONTENT

        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), CODE_CHOOSE_PIC)
    }

    fun handleOpenMediaChooserResult(

        activity: Activity,

        requestCode: Int,
        resultCode: Int,

        resultIntent: Intent?,

        onResult: (filename: String?, mimetype: String?, drawable: Drawable?) -> Unit
    ) {

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == CODE_CHOOSE_PIC) {

                resultIntent?.data?.let { uri ->

                    var inputStream: InputStream? = null
                    var filename: String? = null
                    var mimetype: String? = null
                    var drawable: Drawable? = null

                    try {
                        val resolver = activity.contentResolver

                        inputStream = resolver.openInputStream(uri)
                        mimetype = resolver.getType(uri)

                        resolver.query(uri, null, null, null, null)?.use { cursor ->

                            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)

                            cursor.moveToFirst()

                            filename = cursor.getString(nameIndex)
                        }

                        drawable = Drawable.createFromStream(inputStream, "shsh")
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    } finally {

                        if (inputStream != null) {

                            try {
                                inputStream.close()
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        }

                        onResult(filename, mimetype, drawable)
                    }
                }
            }
        }
    }
}
