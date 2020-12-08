package com.example.githubapi.ui.main.result

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.githubapi.ui.main.MainViewModel
import com.example.githubapi.ui.main.base.BaseFragment
import com.example.githubapi.ui.main.base.BaseListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : BaseFragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: ResultListAdapter

    override fun getItemAdapter(listener: BaseListAdapter.BaseClickListener): BaseListAdapter {
        adapter = ResultListAdapter(listener)
        return adapter
    }

    override fun setObserver() {
        viewModel.searchList.observe(viewLifecycleOwner, Observer {
            println(TAG + "searchList observe called $it")
            if (it != null)
                adapter.submitData(lifecycle, it)
        })

        viewModel.bookmarkLiveData.observe(viewLifecycleOwner, Observer {
            println(TAG + "bookmarkLiveData observe called")
            viewModel.updateSearchList()
            adapter.notifyDataSetChanged()
        })
    }

    companion object {
        const val TAG = "ResultFragment "
    }
}