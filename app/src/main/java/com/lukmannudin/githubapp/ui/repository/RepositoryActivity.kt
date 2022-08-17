package com.lukmannudin.githubapp.ui.repository

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingResource
import com.lukmannudin.githubapp.common.EspressoIdlingResource
import com.lukmannudin.githubapp.common.UiState
import com.lukmannudin.githubapp.common.extension.*
import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.model.User
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
            addOnScrollListener(getScrollListener(linearLayoutManager))
        }
    }

    private fun setupObserver() {
        setUserObserver()
        setRepositoryObserver()
    }

    private fun setUserObserver() {
        viewModel.user.observe(this) { user ->
            setupView(user)
        }
    }

    private fun setRepositoryObserver() {
        viewModel.viewState.observe(this) { viewState ->
            checkIdlingResource(viewState)
            viewState.onLoading {
                setOnLoading(true)
            }
            viewState.onFailure {
                setOnLoading(false)
            }
            viewState.onComplete { repositories ->
                setOnLoading(false)
                repositories?.let {
                    if (viewModel.isOnScrollingPage) {
                        adapter.addAll(it)
                    } else {
                        adapter.clearAndAddAll(it)
                    }
                }
            }
        }
    }

    private fun getScrollListener(linearLayoutManager: LinearLayoutManager): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val lastVisibleItemPosition =
                    linearLayoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisibleItemPosition == adapter.currentList.size - 1) {
                    viewModel.isOnScrollingPage = true
                    viewModel.getRepositories(true)
                }
            }
        }
    }

    private fun setOnLoading(status: Boolean) {
        with(binding.pbProgressbar) {
            if (status) {
                visible()
            } else {
                gone()
            }
        }
    }

    // for testing only
    private fun checkIdlingResource(viewState: UiState<List<Repo>>) {
        if (viewState is UiState.Loading) {
            EspressoIdlingResource.increment()
        } else {
            EspressoIdlingResource.decrement()
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