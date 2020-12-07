package com.example.githubapi.ui.main.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapi.data.Const
import com.example.githubapi.data.entites.GithubRepo
import com.example.githubapi.databinding.BaseFragmentBinding
import com.example.githubapi.ui.detail.DetailActivity
import com.example.githubapi.ui.main.MainActivity
import com.example.githubapi.ui.main.MainViewModel

abstract class BaseFragment : Fragment(),
    BaseListAdapter.BaseClickListener, MainActivity.OnClickSearch {
    public lateinit var binding: BaseFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BaseFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setObserver()
    }

    private fun setRecyclerView() {
        binding.baseRecyclerView.apply {
            val temp = getItemAdapter(this@BaseFragment)
            adapter = temp.withLoadStateFooter(
                footer = CustomLoadStateAdapter(temp::retry)
            )
            layoutManager = this@BaseFragment.getLayoutManager()
        }
    }

    override fun clickedBookmark(isAdd: Boolean, item: GithubRepo) {
        viewModel.clickedBookmark(isAdd, item)
    }

    abstract fun setObserver()

    abstract fun getItemAdapter(listener: BaseListAdapter.BaseClickListener): BaseListAdapter

    private fun getLayoutManager(): LinearLayoutManager =
        LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

    override fun clickedItem(repo: GithubRepo) {
        startActivity(
            Intent(activity, DetailActivity::class.java).apply {
                putExtra(Const.Companion.CLICK_REPOSITORY_ITEM, repo)
            }
        )
    }

    abstract override fun clickSearch(search: String)
}