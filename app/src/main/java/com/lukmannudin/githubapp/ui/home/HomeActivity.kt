package com.lukmannudin.githubapp.ui.home

import android.content.res.TypedArray
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukmannudin.githubapp.R
import com.lukmannudin.githubapp.data.User
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
            addItemDecoration(getDividerItemDecoration())
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

    private fun getDividerItemDecoration(): DividerItemDecoration {
        val att = intArrayOf(android.R.attr.listDivider)

        val typedArray: TypedArray = this.obtainStyledAttributes(att)
        val divider = typedArray.getDrawable(0)
        val inset = resources.getDimensionPixelSize(R.dimen.margin_large)
        val insetDivider = InsetDrawable(divider, inset, 0, inset, 0)
        typedArray.recycle()

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(insetDivider)

        return itemDecoration
    }
}