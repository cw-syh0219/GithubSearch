package com.example.githubapi.data.remote

import com.example.githubapi.data.entites.GithubRepoList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories?per_page=50")
    suspend fun getSearchRepository(
        @Query("q") search: String,
        @Query("page") page: Int
    ): GithubRepoList

//    @GET("character/{id}")
//    suspend fun getCharacter(@Path("id") id: Int): Response<Character>
}