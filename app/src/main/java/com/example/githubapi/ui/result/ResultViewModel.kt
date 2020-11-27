package com.example.githubapi.ui.result

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.githubapi.data.entites.GithubRepoList
import com.example.githubapi.data.repository.GithubRepository
import com.example.githubapi.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultViewModel @ViewModelInject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    private val _repoList = MutableLiveData<Resource<GithubRepoList>>()
    val repoList: LiveData<Resource<GithubRepoList>> get() = _repoList

    fun findRepository(search: String) {
        viewModelScope.launch {
            _repoList.value = withContext(Dispatchers.IO) {
                repository.findRepository(search)
            }
        }
    }
}