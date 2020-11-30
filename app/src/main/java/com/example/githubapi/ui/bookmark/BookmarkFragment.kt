package com.example.githubapi.ui.bookmark

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.ui.base.BaseFragment
import com.example.githubapi.ui.base.BaseListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : BaseFragment() {
    private val viewModel: BookmarkViewModel by viewModels()
    private lateinit var adapter: BookmarkListAdapter

    override fun getItemAdapter(listener: BaseListAdapter.BaseClickListener): BaseListAdapter {
        adapter = BookmarkListAdapter(listener)
        return adapter
    }

    override fun setObserver() {
        viewModel.bookmarkList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "bookmarkList observer called")
            adapter.setItemList(ArrayList(it))
        })
    }

    override fun clickSearch(search: String) {
    }

    companion object {
        const val TAG: String = "BookmarkFragment"
    }
}