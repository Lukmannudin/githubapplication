package com.lukmannudin.githubapp.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.githubapp.common.EspressoIdlingResource
import com.lukmannudin.githubapp.common.UiState
import com.lukmannudin.githubapp.common.extension.*
import com.lukmannudin.githubapp.data.model.Repo
import com.lukmannudin.githubapp.data.model.User
import com.lukmannudin.githubapp.databinding.ActivitySearchUserBinding
import com.lukmannudin.githubapp.ui.repository.RepositoryActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchUserBinding
    private val viewModel: SearchUserViewModel by viewModels()
    private val adapter: SearchUserAdapter by lazy {
        SearchUserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        setupObserver()
        setupAdapter()
    }

    private fun setupObserver() {
        viewModel.viewState.observe(this) { viewState ->
            viewState.onLoading {
                setOnLoading(true)
            }

            viewState.onFailure {
                setOnLoading(false)
            }

            viewState.onComplete { users ->
                setOnLoading(false)
                binding.rvUsers.visible()
                users?.let {
                    if (viewModel.isOnScrollingPage) {
                        adapter.addAll(it)
                    } else {
                        adapter.clearAndAddAll(it)
                    }
                }
            }
        }
    }

    private fun setupView() {
        setupSearchView()
    }

    private fun setupAdapter() {
        val linearLayoutManager = LinearLayoutManager(this)
        with(binding.rvUsers) {
            adapter = this@SearchUserActivity.adapter
            layoutManager = linearLayoutManager
            addOnScrollListener(getScrollListener(linearLayoutManager))
        }

        adapter.onClickItemListener = { user ->
            RepositoryActivity.start(this, user)
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
                    viewModel.search(binding.edtSearch.text.toString())
                }
            }
        }
    }

    private fun setOnLoading(status: Boolean) {
        with(binding.pbHome) {
            if (status) {
                visible()
            } else {
                gone()
            }
        }
    }

    private fun setupSearchView() {
        binding.edtSearch.setOnEditorActionListener { view, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                if (view.text.isNotBlank()) {
                    viewModel.apply {
                        currentPage = 0
                        viewModel.isOnScrollingPage = false
                        search(view.text.toString())
                    }
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }


    // for testing only
    private fun checkIdlingResource(viewState: UiState<List<User>>) {
        if (viewState is UiState.Loading) {
            EspressoIdlingResource.increment()
        } else {
            EspressoIdlingResource.decrement()
        }
    }
}