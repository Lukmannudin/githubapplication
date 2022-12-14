package com.lukmannudin.githubapp.di

import com.lukmannudin.githubapp.data.user.UserRepository
import com.lukmannudin.githubapp.data.user.UserRepositoryImpl
import com.lukmannudin.githubapp.data.user.local.UserDao
import com.lukmannudin.githubapp.data.user.local.UserLocalDataSource
import com.lukmannudin.githubapp.data.user.remote.UserApiService
import com.lukmannudin.githubapp.data.user.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUserDataSourceRemote(
        apiService: UserApiService
    ): UserRemoteDataSource {
        return UserRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideUserDataSourceLocal(
        userDao: UserDao
    ): UserLocalDataSource {
        return UserLocalDataSource(userDao)
    }

    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        userLocalDataSource: UserLocalDataSource
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
    }
}