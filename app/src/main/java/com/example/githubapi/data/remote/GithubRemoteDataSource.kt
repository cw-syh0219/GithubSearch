package com.example.githubapi.data.remote

import androidx.paging.PagingData
import com.example.githubapi.data.entites.GithubRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRemoteDataSource @Inject constructor(
    private val githubService: GithubService
) : BaseDataSource() {
    suspend fun findRepository() = getResult {
        githubService.getAllRepository()
    }

    suspend fun findRepository(search: String, page: Int = 1): Flow<PagingData<GithubRepo>> {
        return githubService.getSearchRepository(search, page)
    }
//    suspend fun getCharacter(id: Int) = getResult { characterService.getCharacter(id) }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepo> {
        val curPage = params.key ?: 1

        return try {
            val response = githubService.getSearchRepository("TEST", curPage)
            LoadResult.Page(
                data = response,    // TODO()
                prevKey = null,
                nextKey = curPage + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}