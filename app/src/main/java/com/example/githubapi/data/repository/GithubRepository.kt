package com.example.githubapi.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.local.RepositoryDao
import com.example.githubapi.data.remote.GithubRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubRemoteDataSource: GithubRemoteDataSource,
    private val githubLocalDataSource: RepositoryDao
) {
//    suspend fun findRepository(search: String): Resource<GithubRepoList> {
//        return githubRemoteDataSource.findRepository(search);
//    }

    suspend fun findRepository(search: String): Flow<PagingData<GithubRepo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = githubRemoteDataSource.load(search, 0) // TODO()
        ).flow
    }

    suspend fun addBookmark(repo: GithubRepo) {
        githubLocalDataSource.addBookmark(repo)
    }

    suspend fun removeBookmark(id: Int) {
        githubLocalDataSource.removeBookmark(id)
    }

    fun getBookmarkList(): LiveData<List<GithubRepo>> {
        return githubLocalDataSource.getAllBookmark()
    }
}

