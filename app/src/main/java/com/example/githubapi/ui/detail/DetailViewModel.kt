package com.example.githubapi.ui.detail

import android.os.Bundle
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.savedstate.SavedStateRegistryOwner
import com.example.githubapi.data.Const
import com.example.githubapi.data.entites.Commit
import com.example.githubapi.data.entites.Commits
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.data.repository.GithubRepository
import kotlinx.coroutines.Dispatchers

class DetailViewModel
constructor(
    bundle: Bundle?,
    repository: GithubRepository
) : ViewModel() {
    val repo: GithubRepo = bundle?.getSerializable(Const.CLICK_REPOSITORY_ITEM) as GithubRepo
    val repoLiveData = liveData<GithubRepo> { emit(repo) }
    var commitList: LiveData<PagingData<Commits>> =
        repository.getCommitList(repo.owner.login, repo.name)
            .asLiveData(Dispatchers.IO)

}

class DetailViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: GithubRepository,
    private val bundle: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, bundle) {
    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ): T {
        return DetailViewModel(bundle, repository) as T
    }
}