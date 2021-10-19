package com.compose.template.db

import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.test.runBlockingTest
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.compose.template.db.dao.TrendingRepoDao
import com.compose.template.models.Repository
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class TrendingRepoDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()

    @Inject
    lateinit var database: GithubDatabase

    private lateinit var trendingRepoDao: TrendingRepoDao

    @Before
    fun setUp() {
        hiltRule.inject()
        trendingRepoDao = database.getTrendingRepoDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert_Multiple_Repositories() {
        runBlockingTest {
            val repo1= Repository(
                name = "Pokedex",author = "Me",avatar = "",builtBy = listOf(),currentPeriodStars = 126,
                description = "a noob project",forks = 20,language = "en",
                languageColor = "red",stars = 200,url = "https://github.com"
            )
            val repo2=repo1.copy(name = "rxKotlin")
            val repo3=repo1.copy(name = "rxBinder")
            trendingRepoDao.insertMultipleRepos(listOf(repo1,repo2,repo3))
            val trendingRepos=trendingRepoDao.getAllRepo()
            assertThat(trendingRepos).containsAtLeast(repo1,repo2,repo3)
        }
    }
}