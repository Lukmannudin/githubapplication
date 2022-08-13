package com.lukmannudin.githubapp.ui.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukmannudin.githubapp.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter: UserCardAdapter by lazy {
        UserCardAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        setupObserver()
        setupAdapter()
    }

    private fun setupAdapter() {
        with(binding.rvUsers) {
            adapter = this@HomeActivity.adapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    private fun setupObserver() {
        viewModel.viewState.observe(this) { viewState ->
            when (viewState) {
                is HomeViewModel.MainViewState.Loading -> {
                    setOnLoading(true)
                }
                is HomeViewModel.MainViewState.UserLoadFailure -> {
                    setOnLoading(false)
                }
                is HomeViewModel.MainViewState.UsersLoaded -> {
                    setOnLoading(false)
                    adapter.addAll(viewState.users)
                }
            }
        }
    }

    private fun setupView() {
        setupSearchView()
    }

    private fun setOnLoading(status: Boolean) {
        with(binding.pbHome) {
            visibility = if (status) {
                View.VISIBLE
            } else {
                View.GONE
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