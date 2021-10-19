package com.compose.template

import com.compose.template.models.Repository
import com.compose.template.remote.GithubApi
import javax.inject.Inject

class MainRepository @Inject
            constructor(private val api: GithubApi) {

    suspend fun fetchRepositories(): List<Repository> {
        return api.fetchRepositories().body()!!
    }
}