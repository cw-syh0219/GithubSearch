package com.example.githubapi.data.remote

import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.repository.GithubRepository.Companion.PAGE_SIZE
import javax.inject.Inject

class GithubRemoteDataSource @Inject constructor(
    private val githubService: GithubService,
    private val search: String
) : BaseDataSource() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepo> {
        val curPage = params.key ?: 1
        var nextKey: Int? = curPage + 1

        return try {
            println("load called")
            val response = githubService.getSearchRepository(search, curPage)
            if (response.total_count < curPage * PAGE_SIZE) {
                nextKey = null
            }

            LoadResult.Page(
                data = response.items,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}