package com.example.githubapi.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.githubapi.data.entites.Commits
import com.example.githubapi.databinding.CommitItemBinding

class CommitListAdapter :
    PagingDataAdapter<Commits, CommitListAdapter.CommitHolder>(REPO_COMPARATOR) {

    lateinit var binding: CommitItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitHolder {
        binding = CommitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommitHolder(binding)
    }

    override fun onBindViewHolder(holder: CommitHolder, position: Int) {
        val commits = getItem(position)
        requireNotNull(commits)

        holder.bind(position, commits)
    }

    inner class CommitHolder(
        private val binding: CommitItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        lateinit var commits: Commits

        fun bind(position: Int, commits: Commits) {
            this.commits = commits

            binding.commitItemMessage.text = commits.commit.message
            binding.commitItemAuthor.text = commits.commit.author.name
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Commits>() {
            override fun areItemsTheSame(oldItem: Commits, newItem: Commits): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Commits, newItem: Commits): Boolean =
                oldItem == newItem
        }
    }

}