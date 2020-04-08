package com.kirkbushman.sampleapp.activities.base

import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity {

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)
    constructor() : super()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> {

                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
