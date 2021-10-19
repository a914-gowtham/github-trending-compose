package com.compose.template.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import android.content.Context
import androidx.room.Room
import com.compose.template.db.GithubDatabase

@Module
@InstallIn(SingletonComponent::class)
object TestDbModule {

    @Provides
    fun provideInMemoryDb(@ApplicationContext context: Context): GithubDatabase{
        return Room.inMemoryDatabaseBuilder(
            context,
            GithubDatabase::class.java
        ).allowMainThreadQueries().build()
    }
}