package com.ticketswapassessment.network

import android.content.Context
import com.ticketswapassessment.ui.activity.ActivityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplicationRepository(@ApplicationContext context: Context): ActivityRepository {
        return ActivityRepository(context = context)
    }
}
