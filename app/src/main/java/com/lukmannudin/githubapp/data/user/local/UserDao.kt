package com.lukmannudin.githubapp.data.user.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE login=:login")
    suspend fun getUserByLogin(login: String): UserLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg users: UserLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg repositories: RepoLocal)

    @Query("SELECT * FROM repository WHERE user_id=:userId")
    suspend fun getRepositoryByUserId(userId: Int): List<RepoLocal>
}