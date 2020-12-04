package com.example.githubapi.data.remote

import com.example.githubapi.data.entites.Commit
import com.example.githubapi.data.entites.Commits
import com.example.githubapi.data.entites.GithubRepoList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories?per_page=50")
    suspend fun getSearchRepository(
        @Query("q") search: String,
        @Query("page") page: Int
    ): GithubRepoList

    @GET("repos/{owner}/{repo}/commits")
    suspend fun getCommitList(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int
    ): List<Commits>
}