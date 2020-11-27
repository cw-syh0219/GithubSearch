package com.example.githubapi.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.githubapi.ui.MainActivity
import com.example.githubapi.ui.base.BaseFragment
import com.example.githubapi.ui.base.BaseListAdapter
import com.example.githubapi.util.Resource
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.repoList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    println("Success Called")
                    adapter.setItemList(ArrayList(it.data!!.items))
                }
                Resource.Status.ERROR -> println("ERROR | ${it.message} | ${it.status}")
                Resource.Status.LOADING -> println("LOADING")
            }
        })
    }

    override fun clickSearch(search: String) {
        println("clickSearch | $search")

        viewModel.findRepository(search)
    }
}