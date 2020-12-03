package com.example.githubapi.ui.bookmark

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.map
import androidx.paging.PagedList
import androidx.paging.PagingData
import com.example.githubapi.ui.base.BaseFragment
import com.example.githubapi.ui.base.BaseListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class BookmarkFragment : BaseFragment() {
    private val viewModel: BookmarkViewModel by activityViewModels()
    private lateinit var adapter: BookmarkListAdapter

    override fun getItemAdapter(listener: BaseListAdapter.BaseClickListener): BaseListAdapter {
        adapter = BookmarkListAdapter(listener)
        return adapter
    }

    override fun setObserver() {
        viewModel.bookmarkList.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launchWhenCreated {
                adapter.submitData(it)
            }
        })
    }

    override fun clickSearch(search: String) {
    }

    companion object {
        const val TAG: String = "BookmarkFragment"
    }
}