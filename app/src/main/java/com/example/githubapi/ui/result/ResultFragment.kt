package com.example.githubapi.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.ui.MainActivity
import com.example.githubapi.ui.MainViewModel
import com.example.githubapi.ui.base.BaseFragment
import com.example.githubapi.ui.base.BaseListAdapter
import com.example.githubapi.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultFragment : BaseFragment() {
    private val viewModel: ResultViewModel by activityViewModels()

    private lateinit var adapter: ResultListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setSearchClickListener(this);
    }


    override fun getItemAdapter(listener: BaseListAdapter.BaseClickListener): BaseListAdapter {
        adapter = ResultListAdapter(listener)
        return adapter
    }

    override fun setObserver() {
//
//        viewModel.repoList.observe(viewLifecycleOwner, Observer {
//            adapter.submitData(it)
//            }
//        })
    }

    override fun clickSearch(search: String) {
        println("clickSearch | $search")

        lifecycleScope.launch {
            viewModel.findRepository(search).collect {
                adapter.submitData(it)
            }
        }
    }
}