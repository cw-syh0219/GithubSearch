package com.example.githubapi.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.entites.GithubRepoList
import com.example.githubapi.data.remote.GithubRemoteDataSource
import com.example.githubapi.util.Resource
import retrofit2.Response
import javax.inject.Inject

class GithubRepository @Inject constructor(private val githubRemoteDataSource: GithubRemoteDataSource) {
    suspend fun findRepository(search: String): Resource<GithubRepoList> {
        return githubRemoteDataSource.findRepository(search);
    }
}

