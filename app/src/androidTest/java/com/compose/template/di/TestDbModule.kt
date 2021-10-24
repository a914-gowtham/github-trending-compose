package com.compose.template.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import android.content.Context
import androidx.room.Room
import com.compose.template.db.GithubDb
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestDbModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context): GithubDb{
        return Room.inMemoryDatabaseBuilder(
            context,
            GithubDb::class.java
        ).allowMainThreadQueries().build()
    }

}