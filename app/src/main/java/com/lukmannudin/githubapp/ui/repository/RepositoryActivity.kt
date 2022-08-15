package com.lukmannudin.githubapp.ui.repository

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukmannudin.githubapp.common.extension.gone
import com.lukmannudin.githubapp.common.extension.showAsCircle
import com.lukmannudin.githubapp.common.extension.showIfNotEmpty
import com.lukmannudin.githubapp.common.extension.visible
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.databinding.ActivityRepositoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepositoryBinding
    private val viewModel: RepositoryViewModel by viewModels()
    private val adapter: RepositoryAdapter by lazy {
        RepositoryAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
        setupAdapter()

        val intent = intent.getParcelableExtra<User>(ARG_USER)
        viewModel.initData(intent)
    }

    private fun setupView(user: User?) {
        user?.let {
            with(binding) {
                ivThumbnailUser.showAsCircle(user.avatarUrl)
                tvUsername.text = user.login
                tvTwitterUsername.showIfNotEmpty(user.twitterUsername)
                tvCompany.showIfNotEmpty(user.company)
                tvFollowersCount.text = user.followers.toString()
                tvFollowingCount.text = user.following.toString()
                tvLocation.showIfNotEmpty(user.location)
                tvEmail.showIfNotEmpty(user.email)
            }

        }
    }

    private fun setupAdapter() {
        val linearLayoutManager = LinearLayoutManager(this@RepositoryActivity)
        with(binding.rvRepository) {
            adapter = this@RepositoryActivity.adapter
            layoutManager = linearLayoutManager
        }
    }

    private fun setupObserver() {
        setUserObserver()
        setRepositoryObserver()
    }

    private fun setUserObserver() {
        viewModel.user.observe(this) { user ->
            adapter.user = user
            setupView(user)
        }
    }

    private fun setRepositoryObserver() {
        viewModel.repositories.observe(this) { viewState ->
            when (viewState) {
                is RepositoryViewModel.RepositoryViewState.Loading -> {
                    setupLoading(true)
                }
                is RepositoryViewModel.RepositoryViewState.RepositoryFailure -> {
                    setupLoading(false)
                }
                is RepositoryViewModel.RepositoryViewState.RepositoryLoaded -> {
                    setupLoading(false)
                    adapter.addAll(viewState.repositories)
                }
            }
        }
    }

    private fun setupLoading(status: Boolean) {
        with(binding.pbProgressbar) {
            if (status) {
                visible()
            } else {
                gone()
            }
        }
    }

    companion object {
        private const val ARG_USER = "ARG_USER"

        fun start(activity: Activity, user: User) {
            val intent = Intent(activity, RepositoryActivity::class.java).apply {
                putExtra(ARG_USER, user)
            }
            activity.startActivity(intent)
        }
    }
}