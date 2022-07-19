package com.rodrigomoreno.moviesmaze.common

import android.app.Application
import kotlinx.coroutines.selects.SelectInstance

class MyApp: Application() {
    companion object{
        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}