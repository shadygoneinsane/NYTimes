package com.assignment.di.module.application

import android.content.Context
import com.assignment.NYApp
import com.assignment.di.qualifier.ApplicationContext
import dagger.Binds
import dagger.Module

@SuppressWarnings
@Module
abstract class AppModule {

    @ApplicationContext
    @Binds
    abstract fun provideApplicationContext(application: NYApp): Context

}