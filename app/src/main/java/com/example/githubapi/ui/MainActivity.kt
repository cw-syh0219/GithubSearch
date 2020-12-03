package com.example.githubapi.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubapi.databinding.MainActivityBinding
import com.example.githubapi.ui.bookmark.BookmarkFragment
import com.example.githubapi.ui.result.ResultFragment
import com.example.githubapi.ui.result.ResultViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var imm: InputMethodManager
    private lateinit var searchClickListener: OnClickSearch
    private val viewModel: ResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        val binding: MainActivityBinding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setLayout(binding)

        viewModel.bookmarkList.observe(this, Observer {
            println("Default request Bookmark database")
        })
    }

    private fun setLayout(binding: MainActivityBinding) {
        binding.mainViewpager.apply {
            adapter = ScreenSlidePagerAdapter(this@MainActivity)
        }

        binding.mainSearchBtn.setOnClickListener {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            searchClickListener.clickSearch(binding.mainSearchEditText.text.toString())
        }

        binding.mainSearchEditText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.mainSearchBtn.callOnClick()
                return@setOnKeyListener true
            }
            false
        }

        TabLayoutMediator(binding.mainTabLayout, binding.mainViewpager) { tab, position ->
            when (position % 2) {
                0 -> tab.text = "Search Result"
                1 -> tab.text = "Bookmark"
            }
        }.attach()
    }

    fun setSearchClickListener(listener: OnClickSearch) {
        searchClickListener = listener
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment =
            if (position % 2 == 0) ResultFragment() else BookmarkFragment()
    }

    interface OnClickSearch {
        fun clickSearch(search: String)
    }

    companion object {
        const val NUM_PAGES = 2
    }
}