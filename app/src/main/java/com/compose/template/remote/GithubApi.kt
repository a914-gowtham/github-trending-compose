package com.compose.template.remote

import com.compose.template.models.Repository
import retrofit2.Response
import retrofit2.http.GET

interface GithubApi {

    @GET("7ef86b70-f1a8-40ab-854c-5d679cd51cd4")
    suspend fun fetchRepositories(): Response<List<Repository>>
}