package com.notification.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.notification.screen.main.MainActivity

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}