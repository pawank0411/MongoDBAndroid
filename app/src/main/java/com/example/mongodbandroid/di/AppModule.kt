package com.example.mongodbandroid.di

import android.content.Context
import com.example.mongodbandroid.BuildConfig
import com.example.mongodbandroid.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApp(@ApplicationContext context: Context): App {
        // ADD YOUR MONGODB APP_ID IN GRADLE.PROPERTIES
        Realm.init(context)
        return App(
            AppConfiguration.Builder(BuildConfig.APP_ID)
                .build()
        )
    }

    @Provides
    @Singleton
    fun provideRepository(app: App) = Repository(app)
}