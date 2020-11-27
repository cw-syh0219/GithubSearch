package com.example.githubapi.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.databinding.MainItemBinding
import com.example.githubapi.ui.base.BaseListAdapter

class BookmarkListAdapter(baseClickListener: BaseClickListener) :
    BaseListAdapter(baseClickListener) {
    private lateinit var binding: MainItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }

    inner class BookmarkHolder(itemBinding: MainItemBinding) : BaseHolder(itemBinding) {
        override fun bind(position: Int, repo: GithubRepo) {
            TODO("Not yet implemented")
        }
    }
}