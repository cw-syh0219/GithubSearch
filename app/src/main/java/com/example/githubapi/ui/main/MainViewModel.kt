package com.example.githubapi.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.*
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class MainViewModel @ViewModelInject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    private lateinit var lastSearch: String
    private var searchFlow: Flow<PagingData<GithubRepo>> = flow { }

    var searchList: MutableLiveData<PagingData<GithubRepo>> = MutableLiveData()
    val bookmarkFlowData = repository.getBookmarkFlow().asLiveData(Dispatchers.IO)
    val bookmarkLiveData = liveData(Dispatchers.IO) {
        Log.d("TEST", "update Data")
        bookmarkList = repository.getBookmarkList()
        emit(bookmarkList)
    }

    lateinit var bookmarkList: List<GithubRepo>

    fun findRepository(search: String) {
        lastSearch = search

        viewModelScope.launch {
            searchFlow = repository.findRepository(search)
            searchFlow.cachedIn(viewModelScope).collectLatest {
                searchList.value = it.map { searchRepo ->
                    searchRepo.isBookmark = isBookmark(searchRepo)
                    searchRepo
                }
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

    private fun addBookmark(inputRepo: GithubRepo) {
        viewModelScope.launch {
            inputRepo.isBookmark = true
            repository.addBookmark(inputRepo)
        }
    }

    private fun removeBookmark(inputRepo: GithubRepo) {
        viewModelScope.launch {
            inputRepo.isBookmark = false
            repository.removeBookmark(inputRepo.id)

            searchFlow.cachedIn(viewModelScope).collectLatest {
                searchList.value = it.map { searchRepo ->
                    searchRepo.isBookmark = isBookmark(searchRepo)
                    searchRepo
                }
            }
        }
    }

    private fun isBookmark(repo: GithubRepo): Boolean {
        var result = false
        for (item in bookmarkList) {
            result = item.id == repo.id
            if (result) {
                Log.d("TEST", "isBookmark ${repo.name}")
                break
            }
        }
        return result
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}
