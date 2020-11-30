package com.example.githubapi.data.entites

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.githubapi.data.local.Converter

@Entity(tableName = "github_repo")
@TypeConverters(Converter::class)
data class GithubRepo(
    @PrimaryKey
    var id: Int,
    var name: String,
    var owner: Owner
) {
    @Ignore
    var isBookmark: Boolean = false
}
