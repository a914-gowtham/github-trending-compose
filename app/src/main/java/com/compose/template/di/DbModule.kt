package com.compose.template.di

import android.content.Context
import androidx.room.Room
import com.compose.template.db.GithubDb
import com.compose.template.utils.Constants.GITHUB_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideGithubDb(@ApplicationContext context: Context): GithubDb {
        return Room.databaseBuilder(
            context, GithubDb::class.java,
            GITHUB_DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideTrendingRepoDao(db: GithubDb) = db.getTrendingRepoDao()
}