package com.example.githubapi.ui.main.bookmark

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.githubapi.ui.main.MainViewModel
import com.example.githubapi.ui.main.base.BaseFragment
import com.example.githubapi.ui.main.base.BaseListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : BaseFragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: BookmarkListAdapter

    override fun getItemAdapter(listener: BaseListAdapter.BaseClickListener): BaseListAdapter {
        adapter = BookmarkListAdapter(listener)
        return adapter
    }

    override fun setObserver() {
        viewModel.bookmarkFlowData.observe(viewLifecycleOwner, Observer {
            adapter.submitData(lifecycle, it)
        })
    }

    override fun clickSearch(search: String) {
    }

    companion object {
        const val TAG: String = "BookmarkFragment"
    }
}