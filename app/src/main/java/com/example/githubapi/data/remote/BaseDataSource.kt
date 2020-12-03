package com.example.githubapi.data.remote

import androidx.paging.PagingSource
import com.example.githubapi.data.entites.GithubRepo

abstract class BaseDataSource : PagingSource<Int, GithubRepo>() {
}