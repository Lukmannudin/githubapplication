package com.lukmannudin.githubapp.di

import com.lukmannudin.githubapp.data.user.UserApiService
import com.lukmannudin.githubapp.data.user.UserRepository
import com.lukmannudin.githubapp.data.user.UserRepositoryImpl
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
    fun provideUserLocalDataSource(
        apiService: UserApiService
    ): UserRemoteDataSource {
        return UserRemoteDataSource(apiService)
    }
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource)
    }
}