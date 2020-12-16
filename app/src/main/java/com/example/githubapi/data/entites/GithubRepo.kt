package com.example.githubapi.data.entites

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.githubapi.data.local.Converter
import java.io.Serializable

@Entity(tableName = "github_repo")
@TypeConverters(Converter::class)
data class GithubRepo(
    @PrimaryKey
    var id: Int,
    var name: String = "",
    var owner: Owner,
    var description: String = "",
    var language: String = "",
    var watchers: String = "",
    var score: Float = 0.0f,
) : Serializable {
    var isBookmark: Boolean = false
}