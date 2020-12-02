package com.example.githubapi.data.remote

import android.util.Log
import androidx.paging.PagingSource
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.util.Resource
import retrofit2.Response

abstract class BaseDataSource : PagingSource<Int, GithubRepo>() {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    return Resource.success(it)
                }
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call has failed for a following reason: $message")
    }
}