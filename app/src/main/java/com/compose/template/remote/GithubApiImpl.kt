package com.compose.template.remote

import com.compose.template.models.Repository
import retrofit2.Response
import javax.inject.Inject

class GithubApiImpl @Inject constructor(
    private val apiService: GithubApi): GithubApi {

    override suspend fun fetchRepositories(): Response<List<Repository>> {
       return apiService.fetchRepositories()
    }

}