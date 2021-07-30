package com.assignment

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import com.assignment.di.DaggerApplicationComponent

/**
 * Application class
 * Created by: Vikesh Dass
 */
class NYApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}