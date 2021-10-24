package com.compose.template.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.compose.template.db.daos.TrendingRepoDao
import com.compose.template.models.Repository

@Database(
    entities = [Repository::class],
    version = 1, exportSchema = false
)
@TypeConverters(GithubTypeConverter::class)
abstract class GithubDb : RoomDatabase() {

    abstract fun getTrendingRepoDao(): TrendingRepoDao

}
