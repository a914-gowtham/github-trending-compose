package com.compose.template

import com.compose.template.db.dao.TrendingRepoDao
import com.compose.template.models.Repository
import com.compose.template.remote.GithubApi
import com.compose.template.utils.Resource
import javax.inject.Inject

class MainRepository @Inject
            constructor(private val api: GithubApi,
                        private val dao: TrendingRepoDao) {

    suspend fun fetchRepositories(): Resource<List<Repository>> {
        var cacheExpired= false
        return if(cacheExpired){
            val response= api.fetchRepositories()
            val newList= response.body() ?: emptyList()
            if(response.isSuccessful && newList.isNotEmpty()){
                dao.nukeTable()
                dao.insertMultipleRepos(newList)
                Resource.Success(newList)
            }else{
                Resource.Error("Something went wrong!")
            }
        }else {
            Resource.Success(dao.getAllRepo())
        }
    }
}