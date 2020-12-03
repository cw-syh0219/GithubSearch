package com.example.githubapi.ui.base

import android.view.View
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.databinding.MainItemBinding
import java.util.*

abstract class BaseListAdapter(private val baseClickListener: BaseClickListener) :
    PagingDataAdapter<GithubRepo, BaseListAdapter.BaseHolder>(REPO_COMPARATOR) {
    //    RecyclerView.Adapter<BaseListAdapter.BaseHolder>() {
    protected lateinit var binding: MainItemBinding

    abstract inner class BaseHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            baseClickListener.clickedItem(v!!)
        }

        abstract fun bind(position: Int, repo: GithubRepo)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val repo: GithubRepo? = getItem(position)
        requireNotNull(repo)

        holder.bind(position, repo)
    }

//    override fun getItemCount(): Int = itemList.size
//
//    fun setItemList(itemList: ArrayList<GithubRepo>) {
//        this.itemList.clear()
//        this.itemList.addAll(itemList)
//        this.notifyDataSetChanged()
//
//        println(itemList)
//    }

    interface BaseClickListener {
        fun clickedItem(item: Any)

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