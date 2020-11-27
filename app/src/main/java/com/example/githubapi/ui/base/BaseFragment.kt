package com.example.githubapi.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapi.databinding.BaseFragmentBinding
import com.example.githubapi.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment : Fragment(),
    BaseListAdapter.BaseClickListener, MainActivity.OnClickSearch {
    private lateinit var binding: BaseFragmentBinding
    private val viewModel: BaseViewModel by viewModels()

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

            adapter = getItemAdapter(this@BaseFragment)
            layoutManager = this@BaseFragment.getLayoutManager()
        }
    }

    abstract fun setObserver()

    abstract fun getItemAdapter(listener: BaseListAdapter.BaseClickListener): BaseListAdapter

    private fun getLayoutManager(): LinearLayoutManager =
        LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

    override fun onClicked(item: Any) {
        Toast.makeText(activity, "Click | ${item}", Toast.LENGTH_SHORT).show()
    }

    abstract override fun clickSearch(search: String)
}