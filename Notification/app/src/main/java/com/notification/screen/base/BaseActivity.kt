package com.notification.screen.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.notification.di.Injectable
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    val PREFERENCES_NOTIFICATION = "PREFERENCES_NOTIFICATION"

    fun saveIntPreferences(key: String, value: Int) {
        val preferences = applicationContext.getSharedPreferences(PREFERENCES_NOTIFICATION, Context.MODE_PRIVATE)
        val preferencesEditor = preferences.edit()
        preferencesEditor.putInt(key, value)
        preferencesEditor.apply()
    }

    fun getIntPreferences(key: String): Int {
        val preferences = applicationContext.getSharedPreferences(PREFERENCES_NOTIFICATION, Context.MODE_PRIVATE)
        return preferences.getInt(key, 0)
    }
}