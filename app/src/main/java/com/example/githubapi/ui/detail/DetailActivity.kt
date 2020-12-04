package com.example.githubapi.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.githubapi.data.repository.GithubRepository
import com.example.githubapi.databinding.DetailActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    lateinit var adapter: CommitListAdapter
    lateinit var binding: DetailActivityBinding

    @Inject
    internal lateinit var repository: GithubRepository

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(this, repository, intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLayout()
        setObserver()
    }

    private fun setLayout() {
        adapter = CommitListAdapter()

        binding.detailCommitList.apply {
            adapter = this@DetailActivity.adapter
            layoutManager = LinearLayoutManager(this@DetailActivity, RecyclerView.VERTICAL, false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setObserver() {
        viewModel.repoLiveData.observe(this, Observer {
            binding.apply {
                detailRepositoryTitle.text = it.name
                detailRepositoryDescription.text =
                    "Language Type : ${it.language}\nWatchers : ${it.watchers} / Score : ${it.score}\n\n ${it.description}"

                Glide.with(this@DetailActivity)
                    .load(it.owner.avatar_url)
                    .transform(CircleCrop())
                    .into(detailProfileImage)
            }
        })

        viewModel.commitList.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }
}