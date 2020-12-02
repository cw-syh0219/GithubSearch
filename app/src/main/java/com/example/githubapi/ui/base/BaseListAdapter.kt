package com.example.githubapi.ui.base

import android.view.View
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.databinding.MainItemBinding
import java.util.ArrayList

abstract class BaseListAdapter(private val baseClickListener: BaseClickListener) :
    PagingDataAdapter<GithubRepo, BaseListAdapter.BaseHolder>(POST_COMPARATOR) {
    //    RecyclerView.Adapter<BaseListAdapter.BaseHolder>() {
    private val itemList = ArrayList<GithubRepo>()
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

    interface BaseClickListener {
        fun clickedItem(item: Any)

        fun clickedBookmark(isAdd: Boolean, item: GithubRepo)
    }

    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<GithubRepo>() {
            override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean =
                oldItem.name == newItem.name

            override fun getChangePayload(oldItem: GithubRepo, newItem: GithubRepo): Any? {
                return if (sameExceptScore(oldItem, newItem)) {
                    PAYLOAD_SCORE
                } else {
                    null
                }
            }
        }

        private fun sameExceptScore(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
            // DON'T do this copy in a real app, it is just convenient here for the demo :)
            // because reddit randomizes scores, we want to pass it as a payload to minimize
            // UI updates between refreshes
            return oldItem.copy(id = newItem.id) == newItem
        }
    }
}