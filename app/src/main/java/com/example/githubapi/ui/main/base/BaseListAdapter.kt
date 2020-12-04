package com.example.githubapi.ui.main.base

import android.view.View
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.databinding.MainItemBinding
import java.util.*

abstract class BaseListAdapter(private val baseClickListener: BaseClickListener) :
    PagingDataAdapter<GithubRepo, BaseListAdapter.BaseHolder>(REPO_COMPARATOR) {
    protected lateinit var binding: MainItemBinding

    abstract inner class BaseHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(position: Int, repo: GithubRepo)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val repo: GithubRepo? = getItem(position)
        requireNotNull(repo)

        holder.bind(position, repo)
    }

    interface BaseClickListener {
        fun clickedItem(repo: GithubRepo)

        fun clickedBookmark(isAdd: Boolean, item: GithubRepo)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GithubRepo>() {
            override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
                val result = oldItem.id == newItem.id && oldItem.isBookmark == newItem.isBookmark
                return result
            }

            override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean =
                oldItem == newItem
        }
    }
}