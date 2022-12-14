package com.lukmannudin.githubapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lukmannudin.githubapp.data.user.local.RepoLocal
import com.lukmannudin.githubapp.data.user.local.UserDao
import com.lukmannudin.githubapp.data.user.local.UserLocal

@Database(entities = [UserLocal::class, RepoLocal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}