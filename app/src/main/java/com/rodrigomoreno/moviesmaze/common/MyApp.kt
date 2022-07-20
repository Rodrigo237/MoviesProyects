package com.rodrigomoreno.moviesmaze.common

import android.app.Application
import kotlinx.coroutines.selects.SelectInstance

class MyApp: Application() {
    //Esta clase permite usar el contexto en todas las partes del proyecto
    companion object{
        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}