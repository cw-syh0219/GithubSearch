package com.example.githubapi.ui.main.result

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.databinding.MainItemBinding
import com.example.githubapi.ui.main.base.BaseListAdapter

class ResultListAdapter(var baseClickListener: BaseClickListener) :
    BaseListAdapter(baseClickListener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding, baseClickListener)
    }

    inner class MainViewHolder(
        private val itemBinding: MainItemBinding,
        private val baseClickListener: BaseClickListener
    ) :
        BaseListAdapter.BaseHolder(itemBinding) {

        lateinit var repo: GithubRepo;

        init {
            itemBinding.mainItemCheckBookmark.setOnClickListener {
                baseClickListener.clickedBookmark((it as CheckBox).isChecked, repo)
            }

            itemBinding.root.setOnClickListener {
                baseClickListener.clickedItem(repo)
            }
        }

        @SuppressLint("SetTextI18n")
        override fun bind(position: Int, repo: GithubRepo) {
            this.repo = repo

            Glide.with(itemBinding.root)
                .load(repo.owner.avatar_url)
                .transform(CircleCrop())
                .into(itemBinding.mainItemRepoProfileImage)

            itemBinding.mainItemRepoName.text = "$position ${repo.name}"
            itemBinding.mainItemRepoOwner.text = repo.owner.login
            itemBinding.mainItemCheckBookmark.isChecked = repo.isBookmark
        }
    }
}

