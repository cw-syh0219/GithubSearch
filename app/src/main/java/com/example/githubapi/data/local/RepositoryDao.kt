package com.example.githubapi.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubapi.data.entites.GithubRepo

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM github_repo")
    fun getBookmarkFlow(): PagingSource<Int, GithubRepo>

    @Query("SELECT * FROM github_repo")
    fun getAllBookmarkList(): List<GithubRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(repo: GithubRepo)

    @Query("DELETE FROM github_repo WHERE id=:id")
    suspend fun removeBookmark(id: Int)

    @Query("DELETE FROM github_repo")
    suspend fun removeAllBookmark()
}