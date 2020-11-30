package com.example.githubapi.ui.base

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.githubapi.data.repository.GithubRepository
import com.example.githubapi.ui.MainViewModel

class BaseViewModel @ViewModelInject constructor(
    repository: GithubRepository
) : MainViewModel(repository) {
}