package com.example.githubapi.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.entites.GithubRepoList
import com.example.githubapi.data.local.RepositoryDao
import com.example.githubapi.data.remote.GithubRemoteDataSource
import com.example.githubapi.data.remote.GithubService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubService: GithubService,
    private val githubRepositoryDao: RepositoryDao
) {
    fun findRepository(search: String): Flow<PagingData<GithubRepo>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
            )
        ) {
            GithubRemoteDataSource(
                search = search,
                githubService = githubService
            )
        }.flow
    }

    fun getBookmarkList(): Flow<PagingData<GithubRepo>> {
        return Pager(
            PagingConfig(PAGE_SIZE)
        ) {
            githubRepositoryDao.getAllBookmark()
        }.flow
    }

    suspend fun addBookmark(repo: GithubRepo) {
        githubRepositoryDao.addBookmark(repo)
    }

    suspend fun removeBookmark(id: Int) {
        githubRepositoryDao.removeBookmark(id)
    }

    companion object {
        const val TAG: String = "GithubRepository"
        const val PAGE_SIZE = 30    //화면에 로드할 개수
    }
}

