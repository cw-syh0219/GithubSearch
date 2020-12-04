package com.example.githubapi.ui.main.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.githubapi.ui.main.MainActivity
import com.example.githubapi.ui.main.MainViewModel
import com.example.githubapi.ui.main.base.BaseFragment
import com.example.githubapi.ui.main.base.BaseListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : BaseFragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: ResultListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setSearchClickListener(this)
    }


    override fun getItemAdapter(listener: BaseListAdapter.BaseClickListener): BaseListAdapter {
        adapter = ResultListAdapter(listener)
        return adapter
    }

    override fun setObserver() {
        viewModel.searchList.observe(viewLifecycleOwner, Observer {
            adapter.submitData(lifecycle, it)
        })
    }

    override fun clickSearch(search: String) {
        println("clickSearch | $search")

        viewModel.findRepository(search)
    }
}