package com.example.githubapi.ui.result

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.entites.GithubRepoList
import com.example.githubapi.data.repository.GithubRepository
import com.example.githubapi.ui.MainViewModel
import com.example.githubapi.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultViewModel @ViewModelInject constructor(
    private val repository: GithubRepository
) : MainViewModel(repository) {
    private lateinit var lastSearch: String
    private lateinit var _repoList: Flow<PagingData<GithubRepo>>

    suspend fun findRepository(search: String): Flow<PagingData<GithubRepo>> {
        lastSearch = search

        val newResult: Flow<PagingData<GithubRepo>> = repository.findRepository(search)
        _repoList = newResult
        return newResult
    }

//    private val _repoList = MutableLiveData<Resource<GithubRepoList>>()
//    val repoList: LiveData<Resource<GithubRepoList>>
//        get() {
//            return _repoList.map {
//                it.data?.items?.let { items ->
//                    for (repo in items) {
//                        repo.isBookmark = this.isBookmark(repo)
//                    }
//                }
//                return@map it;
//            }
//        }
//
//    fun findRepository(search: String) {
//        viewModelScope.launch {
//            _repoList.value = withContext(Dispatchers.IO) {
//                repository.findRepository(search)
//            }
//        }
//    }
}