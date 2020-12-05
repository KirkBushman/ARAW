package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.kirkbushman.sampleapp.activities.Settings.FLAG_DISABLE_LEGACY_ENC
import com.kirkbushman.sampleapp.activities.Settings.PREFS_NAME
import com.kirkbushman.sampleapp.databinding.ActivitySettingsBinding

object Settings {

    const val PREFS_NAME = "araw_sampleapp_shared_prefs"
    const val FLAG_DISABLE_LEGACY_ENC = "flag_disable_legacy_encoding"
}

class SettingsActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }

        private var prefs: SharedPreferences? = null

        fun getPrefs(context: Context): SharedPreferences {

            if (prefs == null) {
                prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            }

            return prefs!!
        }
    }

    private val prefs by lazy { getPrefs(this) }
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.disableLegacyEncoding.isChecked = prefs.getDisableLegacyEncoding()
        binding.disableLegacyEncoding.setOnCheckedChangeListener { _, isChecked ->

            prefs.setDisabledLegacyEncoding(isChecked)
        }
    }
}

fun SharedPreferences.getDisableLegacyEncoding(): Boolean {
    return getBoolean(FLAG_DISABLE_LEGACY_ENC, false)
}

fun SharedPreferences.setDisabledLegacyEncoding(disabled: Boolean) {

    edit {
        putBoolean(FLAG_DISABLE_LEGACY_ENC, disabled)
    }
}
