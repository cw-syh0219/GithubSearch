package com.example.githubapi.ui.main

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.repository.GithubRepository
import com.example.githubapi.util.workManager.BackgroundWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

open class MainViewModel @ViewModelInject constructor(
    application: Application,
    private val repository: GithubRepository
) : AndroidViewModel(application) {
    private var searchFlow: Flow<PagingData<GithubRepo>> = flow { }
    var searchList: MutableLiveData<PagingData<GithubRepo>> = MutableLiveData()

    var bookmarkList: List<GithubRepo> = mutableListOf()
    val bookmarkFlowData =
        repository.getBookmarkFlow().cachedIn(viewModelScope).asLiveData(Dispatchers.IO)
    val bookmarkLiveData = liveData(Dispatchers.IO) {
        emitSource(repository.getBookmarkList())
    }


    fun findRepository(search: String) {
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
        }
    }

    fun updateSearchList() {
        searchList.value = searchList.value?.map { searchRepo ->
            searchRepo.isBookmark = isBookmark(searchRepo)
            searchRepo
        }
    }

    private fun isBookmark(repo: GithubRepo): Boolean {
        var result = false
        for (item in bookmarkList) {
            result = item.id == repo.id
            if (result) {
                println(TAG + "isBookmark ${repo.name}")
                break
            }
        }
        return result
    }

    // TODO WorkManger 테스트 코드
    public fun applyWorker() {
        println(TAG + "applyWorker apply worker")

        val save = OneTimeWorkRequestBuilder<BackgroundWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
//                  .setRequiresCharging(true)
                    .build()
            )
            .build()

        val continuation = WorkManager.getInstance(getApplication()).beginWith(save)
        continuation.enqueue()
    }

    companion object {
        const val TAG = "MainViewModel "
    }
}
