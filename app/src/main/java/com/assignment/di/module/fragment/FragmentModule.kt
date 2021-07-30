package com.assignment.di.module.fragment;

import com.assignment.ui.detail.DetailFragment;
import com.assignment.ui.main.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();

    @ContributesAndroidInjector
    abstract DetailFragment contributeDetailFragment();
}