package com.example.githubapi.ui.result

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.databinding.MainItemBinding
import com.example.githubapi.ui.base.BaseListAdapter
import java.util.*

class ResultListAdapter(baseClickListener: BaseClickListener) : BaseListAdapter(baseClickListener) {
    private val itemList = ArrayList<GithubRepo>()
    private lateinit var binding: MainItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val repo: GithubRepo = itemList[position]

        holder.bind(position, repo)
    }

    override fun getItemCount(): Int = itemList.size

    fun setItemList(itemList: ArrayList<GithubRepo>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        this.notifyDataSetChanged()

        println(itemList)
    }

    inner class MainViewHolder(private val itemBinding: MainItemBinding) :
        BaseListAdapter.BaseHolder(itemBinding) {

        @SuppressLint("SetTextI18n")
        override fun bind(position: Int, repo: GithubRepo) {
            Glide.with(itemBinding.root)
                .load(repo.owner.avatar_url)
                .transform(CircleCrop())
                .into(itemBinding.mainItemRepoProfileImage)

            itemBinding.mainItemRepoName.text = "$position ${repo.name}"
            itemBinding.mainItemRepoOwner.text = repo.owner.login
        }
    }
}

