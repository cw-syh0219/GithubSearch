package com.example.githubapi.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.map
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class MainViewModel @ViewModelInject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    private lateinit var lastSearch: String
    var searchList: MutableLiveData<PagingData<GithubRepo>> = MutableLiveData()
    val bookmarkList = repository.getBookmarkList().asLiveData(Dispatchers.IO)

    fun findRepository(search: String) {
        lastSearch = search

        viewModelScope.launch {
            repository.findRepository(search).collectLatest {
                searchList.value = it
            }
        }
    }

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

        searchList.value = searchList.value?.map { repository ->
            if (repository.id == repo.id) {
                repository.isBookmark = true
            }
            repository
        }
    }

    private fun removeBookmark(repo: GithubRepo) {
        viewModelScope.launch {
            repository.removeBookmark(repo.id)
        }

        searchList.value = searchList.value?.map { it ->
            if (it.id == repo.id) {
                it.isBookmark = false
            }
            it
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}
