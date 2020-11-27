package com.example.githubapi.ui.bookmark

import androidx.fragment.app.viewModels
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
//        viewModel.repoList.observe(viewLifecycleOwner, Observer {
//            when (it.status) {
//                Resource.Status.SUCCESS -> {
//                    print("Success Called")
//                    adapter.setItemList(it.data!!.items)
//                }
//                Resource.Status.ERROR -> print("TEST")
//                Resource.Status.LOADING -> print("LOADING")
//            }
//        })
    }

    override fun clickSearch(search: String) {
        TODO("Not yet implemented")
    }
}