package com.arbaelbarca.posfantastic.ui.di

import com.arbaelbarca.posfantastic.ui.domain.repository.users.UsersIRepository
import com.arbaelbarca.posfantastic.ui.domain.repository.users.UsersRepository
import com.arbaelbarca.posfantastic.ui.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUsersRepository(apiService: ApiService): UsersIRepository {
        return UsersRepository(apiService)
    }
}