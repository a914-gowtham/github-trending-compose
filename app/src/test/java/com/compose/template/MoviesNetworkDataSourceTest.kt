package com.compose.template

import com.compose.template.remote.GithubApi
import com.compose.template.remote.GithubApiImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

class MoviesNetworkDataSourceTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val githubApi = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GithubApi::class.java)

    private lateinit var apiImpl: GithubApi

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Before
    fun setUp() {
        apiImpl = GithubApiImpl(apiService = githubApi)
    }

    @Test
    fun `should fetch repositories correctly given 200 response`() {
        val response = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody("[]")
            .setResponseCode(200)
        mockWebServer.enqueue(response)

        runBlocking {
            val actual = githubApi.fetchRepositories()

            response.throttleBody(1024, 1, TimeUnit.SECONDS)

            val responseCode= actual.code()
            assertThat(responseCode).isEqualTo(200)
        }
    }

    @Test
    fun `should fetch repositories correctly given correct list response`() {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/trending-repo-200.json")

        val source = inputStream?.let { inputStream.source().buffer() }

        val response = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody(source?.readString(StandardCharsets.UTF_8) ?: "[]")
            .setResponseCode(200)
        mockWebServer.enqueue(response)

        runBlocking {
            val actual = githubApi.fetchRepositories()

            response.throttleBody(1024, 1, TimeUnit.SECONDS)

            val responseCode= actual.body() ?: emptyList();
            assertThat(responseCode).isNotEmpty()
        }
    }

}