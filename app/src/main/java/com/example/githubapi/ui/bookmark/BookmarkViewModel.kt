package com.example.githubapi.ui.bookmark

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.githubapi.data.repository.GithubRepository
import com.example.githubapi.ui.MainViewModel

class BookmarkViewModel @ViewModelInject constructor(
    repository: GithubRepository
) : MainViewModel(repository) {

    init {
        Log.d(TAG, "Bookmark init called ${bookmarkList.value} ${bookmarkList.value?.size}")
    }

    companion object {
        const val TAG: String = "BookmarkViewModel"
    }
}