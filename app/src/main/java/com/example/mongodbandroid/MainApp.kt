package com.example.mongodbandroid

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.Credentials
import javax.inject.Inject

@HiltAndroidApp
class MainApp : Application() {

    @Inject
    lateinit var app: App

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        val anonymousCredentials: Credentials = Credentials.anonymous()
        app.loginAsync(anonymousCredentials) {
            if (it.isSuccess)
                println("Successfully authenticated anonymously.")
            else
                println("Failed ${it.error}")
        }
    }
}