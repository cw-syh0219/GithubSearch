package com.example.githubapi.data.remote

import androidx.paging.PagingSource
import com.example.githubapi.data.entites.Commits

class CommitRemoteDataSource(
    private val githubService: GithubService,
    private val owner: String,
    private val repo: String
) : PagingSource<Int, Commits>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Commits> {
        val curPage = params.key ?: 1
        var nextKey: Int? = curPage + 1

        return try {
            val response = githubService.getCommitList(owner, repo, curPage)
            if (response.isEmpty()) nextKey = null

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }


    }
}