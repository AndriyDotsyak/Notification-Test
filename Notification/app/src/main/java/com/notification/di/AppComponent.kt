package com.notification.di

import com.notification.NotificationApp
import com.notification.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        MainActivityModule::class]
)

interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: NotificationApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: NotificationApp)
}