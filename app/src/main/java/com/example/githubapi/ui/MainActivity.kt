package com.example.githubapi.ui

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubapi.databinding.MainActivityBinding
import com.example.githubapi.ui.bookmark.BookmarkFragment
import com.example.githubapi.ui.result.ResultFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var imm: InputMethodManager
    private lateinit var searchClickListener: OnClickSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        val binding: MainActivityBinding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setLayout(binding)
    }

    private fun setLayout(binding: MainActivityBinding) {
        binding.mainViewpager.apply {
            adapter = ScreenSlidePagerAdapter(this@MainActivity)
        }

        binding.mainSearchBtn.setOnClickListener {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            searchClickListener.clickSearch(binding.mainSearchEditText.text.toString())
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