package com.assignment.di.module.activity;

import com.assignment.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector()
    public abstract MainActivity contributeMainActivity();
}