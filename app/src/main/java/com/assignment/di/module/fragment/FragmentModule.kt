package com.assignment.di.module.fragment

import com.assignment.ui.detail.DetailFragment
import com.assignment.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment?
    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment?
}