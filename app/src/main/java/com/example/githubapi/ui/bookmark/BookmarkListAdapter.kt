package com.example.githubapi.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.databinding.MainItemBinding
import com.example.githubapi.ui.base.BaseListAdapter

class BookmarkListAdapter(private val baseClickListener: BaseClickListener) :
    BaseListAdapter(baseClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkHolder(binding, baseClickListener)
    }

    inner class BookmarkHolder(
        private val itemBinding: MainItemBinding,
        private val baseClickListener: BaseClickListener
    ) : BaseHolder(itemBinding) {

        lateinit var repo: GithubRepo;

        init {
            itemBinding.mainItemCheckBookmark.setOnClickListener {
                baseClickListener.clickedBookmark((it as CheckBox).isChecked, repo)
            }
        }

        override fun bind(position: Int, repo: GithubRepo) {
            this.repo = repo

            Glide.with(itemBinding.root)
                .load(repo.owner.avatar_url)
                .transform(CircleCrop())
                .into(itemBinding.mainItemRepoProfileImage)


            itemBinding.mainItemRepoName.text = "$position ${repo.name}"
            itemBinding.mainItemRepoOwner.text = repo.owner.login
            itemBinding.mainItemCheckBookmark.isChecked = true
        }
    }
}