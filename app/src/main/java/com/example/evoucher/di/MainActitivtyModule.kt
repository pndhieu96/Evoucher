package com.example.codebaseandroidapp.di

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.example.evoucher.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Qualifier

@Module
@InstallIn(ActivityComponent::class)
abstract class MainActitvityAbstractModule {

}

@Module
@InstallIn(ActivityComponent::class)
class MainActitvityModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainActivityFragmentManager

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainActivityLifeCycle

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainActivityBinding

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainActivityTabLayoutMediator

    @Provides
    @MainActivityFragmentManager
    fun provideMainActivityFragmentManager(@ActivityContext context: Context) : FragmentManager {
        return (context as MainActivity).supportFragmentManager
    }

    @Provides
    @MainActivityLifeCycle
    fun provideMainActivityLifCycle(@ActivityContext context: Context) : Lifecycle {
        return (context as MainActivity).lifecycle
    }
}