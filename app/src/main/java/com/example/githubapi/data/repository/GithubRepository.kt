package com.example.githubapi.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.githubapi.data.entites.Commits
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.local.RepositoryDao
import com.example.githubapi.data.remote.CommitRemoteDataSource
import com.example.githubapi.data.remote.GithubRemoteDataSource
import com.example.githubapi.data.remote.GithubService
import kotlinx.coroutines.flow.Flow
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

    fun getBookmarkFlow(): Flow<PagingData<GithubRepo>> {
        return Pager(
            PagingConfig(PAGE_SIZE)
        ) {
            githubRepositoryDao.getBookmarkFlow()
        }.flow
    }

    fun getBookmarkList(): List<GithubRepo> {
        return githubRepositoryDao.getAllBookmarkList()
    }

    suspend fun addBookmark(repo: GithubRepo) {
        githubRepositoryDao.addBookmark(repo)
    }

    suspend fun removeBookmark(id: Int) {
        githubRepositoryDao.removeBookmark(id)
    }

    fun getCommitList(owner: String, repo: String): Flow<PagingData<Commits>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
            )
        ) {
            CommitRemoteDataSource(
                githubService = githubService,
                owner, repo
            )
        }.flow
    }

    companion object {
        const val TAG: String = "GithubRepository"
        const val PAGE_SIZE = 30    //화면에 로드할 개수
    }
}

