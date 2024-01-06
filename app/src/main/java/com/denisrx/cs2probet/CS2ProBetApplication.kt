package com.denisrx.cs2probet

import android.app.Application
import com.denisrx.cs2probet.data.AppContainer
import com.denisrx.cs2probet.data.DefaultAppContainer

class CS2ProBetApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}
