package com.example.project_tomerfridler

import android.app.Application
import com.example.project_tomerfridler.utilities.SignalManager

class App: Application(){

    override fun onCreate() {
        super.onCreate()
        SignalManager.init(this)
    }

}