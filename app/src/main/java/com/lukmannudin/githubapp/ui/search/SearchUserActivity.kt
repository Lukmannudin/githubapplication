package com.lukmannudin.githubapp.ui.search

import android.content.res.TypedArray
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukmannudin.githubapp.R
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
                    adapter.addAll(viewState.users)
                }
            }
        }
    }

    private fun setupView() {
        setupSearchView()
    }

    private fun setupAdapter() {
        with(binding.rvUsers) {
            adapter = this@SearchUserActivity.adapter
            layoutManager = LinearLayoutManager(this@SearchUserActivity)
        }

        adapter.onClickItemListener = { user ->
            RepositoryActivity.start(this, user)
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
                    viewModel.search(view.text.toString(), 0)
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}