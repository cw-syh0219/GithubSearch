package com.example.githubapi.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class MainViewModel @ViewModelInject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _bookmarkList = liveData(Dispatchers.IO) {
        emitSource(repository.getBookmarkList())
    }

    val bookmarkList: LiveData<List<GithubRepo>>
        get() = _bookmarkList

    fun clickedBookmark(isAdd: Boolean, item: GithubRepo) {
        println("clickedBookmark | $isAdd $item")

        when (isAdd) {
            true -> addBookmark(item)
            false -> removeBookmark(item)
        }
    }

    private fun addBookmark(repo: GithubRepo) {
        viewModelScope.launch {
            repository.addBookmark(repo)
        }
    }

    private fun removeBookmark(repo: GithubRepo) {
        viewModelScope.launch {
            repository.removeBookmark(repo.id)
        }
    }

    fun isBookmark(repo: GithubRepo): Boolean {
        val isBookmark = bookmarkList.value?.contains(repo) ?: false
        println("isBookmark | $isBookmark ${bookmarkList.value}")
        return isBookmark
    }

//    companion object {
//        lateinit var bookmarkList: List<GithubRepo>
//    }
}
