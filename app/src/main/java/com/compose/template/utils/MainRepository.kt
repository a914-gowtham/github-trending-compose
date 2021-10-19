package com.compose.template.utils

import com.compose.template.models.Repository

interface MainRepository {

    suspend fun fetchRepositories(): List<Repository>
}