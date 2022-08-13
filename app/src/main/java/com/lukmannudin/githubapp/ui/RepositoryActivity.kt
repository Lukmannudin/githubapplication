package com.lukmannudin.githubapp.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lukmannudin.githubapp.R
import com.lukmannudin.githubapp.data.User
import com.lukmannudin.githubapp.databinding.ActivityRepositoryBinding

class RepositoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepositoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        private const val ARG_USER = "ARG_USER"

        fun start(activity: Activity, user: User) {
            val intent = Intent().apply {
                putExtra(ARG_USER, user)
            }
            activity.startActivity(intent)
        }
    }
}