package com.compose.template.di

import android.content.Context
import androidx.room.Room
import com.compose.template.db.GithubDatabase
import com.compose.template.utils.Constants.GITHUB_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideGithubDb(@ApplicationContext context: Context): GithubDatabase {
        return Room.databaseBuilder(
            context, GithubDatabase::class.java,
            GITHUB_DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideTrendingRepoDao(db: GithubDatabase) = db.getTrendingRepoDao()

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context): GithubDatabase{
        return Room.inMemoryDatabaseBuilder(
            context,
            GithubDatabase::class.java
        ).allowMainThreadQueries().build()
    }

}