package com.example.githubapi.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.githubapi.data.entites.GithubRepo

abstract class BaseListAdapter(private val baseClickListener: BaseClickListener) :
    RecyclerView.Adapter<BaseListAdapter.BaseHolder>() {

    abstract inner class BaseHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            baseClickListener.onClicked(v!!)
        }

        abstract fun bind(position: Int, repo: GithubRepo)
    }

    interface BaseClickListener {
        fun onClicked(item: Any)
    }
}