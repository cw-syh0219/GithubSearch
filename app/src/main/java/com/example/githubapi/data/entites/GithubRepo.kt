package com.example.githubapi.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_repo")
data class GithubRepo(
    @PrimaryKey
    var id: Int,
    var name: String,
    var owner: Owner
)