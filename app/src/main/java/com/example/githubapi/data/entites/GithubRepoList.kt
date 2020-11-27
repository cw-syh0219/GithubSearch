package com.example.githubapi.data.entites

data class GithubRepoList(
    val total_count: Int, val items: List<GithubRepo>
)