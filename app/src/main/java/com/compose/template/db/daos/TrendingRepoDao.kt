package com.compose.template.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compose.template.models.Repository

@Dao
interface TrendingRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleRepos(users: List<Repository>)

    @Query("SELECT * FROM Repository")
    suspend fun getAllRepo(): List<Repository>

    @Query("DELETE FROM Repository")
    suspend fun nukeTable()
}