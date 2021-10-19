package com.compose.template.db.dao

import androidx.room.Insert
import androidx.room.Query
import com.compose.template.models.Repository

interface TrendingRepoDao {

    @Insert
    suspend fun insertMultipleRepos(users: List<Repository>)

    @Query("SELECT * FROM Repository")
    suspend fun getAllRepo(): List<Repository>

    @Query("DELETE FROM Repository")
    suspend fun nukeTable()
}