package com.example.reserbar2

import android.app.Application
import com.example.reserbar2.data.di.AppContainer
import com.example.reserbar2.data.di.DefaultAppContainer

class Application : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}