package com.example.githubapi.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.databinding.MainItemBinding
import java.util.ArrayList

abstract class BaseListAdapter(private val baseClickListener: BaseClickListener) :
    RecyclerView.Adapter<BaseListAdapter.BaseHolder>() {
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
}