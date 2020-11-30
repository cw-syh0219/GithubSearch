package com.example.githubapi.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.entites.GithubRepoList
import com.example.githubapi.data.local.RepositoryDao
import com.example.githubapi.data.remote.GithubRemoteDataSource
import com.example.githubapi.util.Resource
import retrofit2.Response
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubRemoteDataSource: GithubRemoteDataSource,
    private val githubLocalDataSource: RepositoryDao
) {
    suspend fun findRepository(search: String): Resource<GithubRepoList> {
        return githubRemoteDataSource.findRepository(search);
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

