package com.example.githubapi.data.remote

import com.example.githubapi.data.entites.GithubRepoList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories?q=coway")
    suspend fun getAllRepository(): Response<GithubRepoList>

    @GET("search/repositories")
    suspend fun getSearchRepository(@Query("q") search: String): Response<GithubRepoList>

//    @GET("character/{id}")
//    suspend fun getCharacter(@Path("id") id: Int): Response<Character>
}