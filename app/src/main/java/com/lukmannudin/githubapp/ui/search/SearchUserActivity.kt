package com.lukmannudin.githubapp.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.githubapp.common.gone
import com.lukmannudin.githubapp.common.visible
import com.lukmannudin.githubapp.databinding.ActivityHomeBinding
import com.lukmannudin.githubapp.ui.repository.RepositoryActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: SearchViewModel by viewModels()
    private val adapter: SearchUserAdapter by lazy {
        SearchUserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        setupObserver()
        setupAdapter()
    }

    private fun setupObserver() {
        viewModel.viewState.observe(this) { viewState ->
            when (viewState) {
                is SearchViewModel.MainViewState.Loading -> {
                    setOnLoading(true)
                }
                is SearchViewModel.MainViewState.UserLoadFailure -> {
                    setOnLoading(false)
                }
                is SearchViewModel.MainViewState.UsersLoaded -> {
                    setOnLoading(false)
                    binding.rvUsers.visible()
                    adapter.addAll(viewState.users)
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
        }

        binding.rvUsers.addOnScrollListener(getScrollListener(linearLayoutManager))
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
                        search(view.text.toString())
                    }
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}