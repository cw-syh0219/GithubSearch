package com.example.githubapi.data.remote

import javax.inject.Inject

class GithubRemoteDataSource @Inject constructor(
    private val githubService: GithubService
) : BaseDataSource() {
    suspend fun findRepository() = getResult {
        githubService.getAllRepository()
    }

    suspend fun findRepository(search: String) = getResult {
        githubService.getSearchRepository(search)
    }
//    suspend fun getCharacter(id: Int) = getResult { characterService.getCharacter(id) }
}