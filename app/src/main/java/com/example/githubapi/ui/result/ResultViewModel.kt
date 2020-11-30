package com.example.githubapi.ui.result

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.entites.GithubRepoList
import com.example.githubapi.data.repository.GithubRepository
import com.example.githubapi.ui.MainViewModel
import com.example.githubapi.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultViewModel @ViewModelInject constructor(
    private val repository: GithubRepository
) : MainViewModel(repository) {

    private val _repoList = MutableLiveData<Resource<GithubRepoList>>()
    val repoList: LiveData<Resource<GithubRepoList>>
        get() {
            return _repoList.map {
                for (repo in it.data!!.items) {
                    repo.isBookmark = this.isBookmark(repo)
                }
                return@map it;
            }
        }

    fun findRepository(search: String) {
        viewModelScope.launch {
            _repoList.value = withContext(Dispatchers.IO) {
                repository.findRepository(search)
            }
        }
    }
}