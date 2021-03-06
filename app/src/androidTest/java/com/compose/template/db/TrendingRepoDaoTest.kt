package com.compose.template.db

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.compose.template.db.daos.TrendingRepoDao
import com.compose.template.di.DbModule
import com.compose.template.models.Repository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.UninstallModules

@ExperimentalCoroutinesApi
@HiltAndroidTest
class TrendingRepoDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var db: GithubDb

    private lateinit var trendingRepoDao: TrendingRepoDao

    @Before
    fun setUp() {
        hiltRule.inject()
        trendingRepoDao = db.getTrendingRepoDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun dummy_test() {
        val a=4
        val b=4
        assertThat(a+b).isEqualTo(8)
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
            val trendingRepos= trendingRepoDao.getAllRepo()
            assertThat(trendingRepos).containsExactly(repo1,repo2,repo3)
        }
    }

    @Test
    fun update_New_Repositories() {
        runBlockingTest {
            val oldRepoList = mutableListOf<Repository>()
            val oldRepo1 = Repository(
                name = "Pokedex",
                author = "Me",
                avatar = "",
                builtBy = listOf(),
                currentPeriodStars = 126,
                description = "a noob project",
                forks = 20,
                language = "en",
                languageColor = "red",
                stars = 200,
                url = "https://github.com"
            )
            val oldRepo2 = oldRepo1.copy(name = "rxKotlin")
            val oldRepo3 = oldRepo1.copy(name = "rxBinder")
            oldRepoList.add(oldRepo1)
            oldRepoList.add(oldRepo2)
            oldRepoList.add(oldRepo3)
            trendingRepoDao.insertMultipleRepos(oldRepoList)
            trendingRepoDao.nukeTable()

            val newRepoList=mutableListOf<Repository>()
            val newRepo1= Repository(
                name = "Coil",author = "Me",avatar = "",builtBy = listOf(),currentPeriodStars = 126,
                description = "a noob project",forks = 20,language = "en",
                languageColor = "red",stars = 200,url = "https://github.com"
            )
            val newRepo2=newRepo1.copy(name = "RxAndroid")
            val newRepo3=newRepo1.copy(name = "Glide")
            newRepoList.add(newRepo1)
            newRepoList.add(newRepo2)
            newRepoList.add(newRepo3)
            trendingRepoDao.insertMultipleRepos(newRepoList)

            val trendingRepos=trendingRepoDao.getAllRepo()
            assertThat(trendingRepos).containsExactlyElementsIn(newRepoList)
        }
    }

    @Test
    fun delete_All_Repositories() {
        runBlockingTest {
            val repo1= Repository(
                name = "Pokedex",author = "Me",avatar = "",builtBy = listOf(),currentPeriodStars = 126,
                description = "a noob project",forks = 20,language = "en",
                languageColor = "red",stars = 200,url = "https://github.com"
            )
            val repo2=repo1.copy(name = "rxKotlin")
            val repo3=repo1.copy(name = "rxBinder")
            trendingRepoDao.insertMultipleRepos(listOf(repo1,repo2,repo3))
            trendingRepoDao.nukeTable()
            val trendingRepos=trendingRepoDao.getAllRepo()
            assertThat(trendingRepos).isEmpty()
        }
    }
}