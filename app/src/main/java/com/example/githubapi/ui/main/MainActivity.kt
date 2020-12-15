package com.example.githubapi.ui.main

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubapi.R
import com.example.githubapi.databinding.MainActivityBinding
import com.example.githubapi.ui.main.bookmark.BookmarkFragment
import com.example.githubapi.ui.main.result.ResultFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var imm: InputMethodManager
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        val binding: MainActivityBinding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setLayout(binding)

        viewModel.bookmarkLiveData.observe(this, Observer {
            println("Default request Bookmark database1")
            viewModel.bookmarkList = it
        })

        viewModel.bookmarkFlowData.observe(this, Observer {
            println("Default request Bookmark database2")
        })

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token as String
            Log.d(TAG, "TOKEN | " + token)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setLayout(binding: MainActivityBinding) {
        binding.mainViewpager.apply {
            adapter = ScreenSlidePagerAdapter(this@MainActivity)
        }

        binding.mainSearchBtn.setOnClickListener {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            binding.mainViewpager.setCurrentItem(0, true)

            binding.mainSearchEditText.text.toString().let { searchText ->
                if (TextUtils.isEmpty(searchText)) {
                    Toast.makeText(
                        this@MainActivity,
                        "Search text is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.findRepository(searchText)
                }
            }
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

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = VIEW_PAGER_PAGE_COUNT

        override fun createFragment(position: Int): Fragment =
            if (position % 2 == 0) ResultFragment() else BookmarkFragment()
    }

    companion object {
        const val VIEW_PAGER_PAGE_COUNT = 2
        const val TAG = "MainActivity"
    }


//    override fun onDestroy() {
//        super.onDestroy()

//        viewModel.applyWorker()
//    }
}