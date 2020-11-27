package com.example.githubapi

import android.util.Log
import retrofit2.Retrofit
import javax.inject.Inject

class test @Inject constructor(retrofit: Retrofit) {
    init {
        Log.d("YHSON", " " + retrofit.baseUrl())
    }
}