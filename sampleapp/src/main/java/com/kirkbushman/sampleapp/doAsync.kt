package com.kirkbushman.sampleapp

import android.os.AsyncTask

class doAsync(private val doWork: () -> Unit, private val onPost: (() -> Unit)? = null) : AsyncTask<Void, Void, Void>() {
    init {
        execute()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        doWork()
        return null
    }

    override fun onPostExecute(result: Void?) {
        onPost?.invoke()
    }
}
