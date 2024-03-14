package com.binyouwei.appcommunicationassistant

import android.app.Application
import android.content.Context

/**
 * @author binjx
 * @date 2024/3/13 17:24
 * @purpose：
 **/
class App : Application() {

    init {
        instance = requireNotNull(this)
    }

    companion object {
        private lateinit var instance: App

        fun applicationContext(): Context {
            return instance
        }
    }
}