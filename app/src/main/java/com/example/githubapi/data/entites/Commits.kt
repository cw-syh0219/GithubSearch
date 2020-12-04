package com.example.githubapi.data.entites

data class Commits(val commit: Commit)

data class Commit(val author: Author, val message: String)

data class Author(val name: String)