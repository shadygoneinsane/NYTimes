package com.assignment.di.module.application

import com.assignment.data.datasource.remote.api.INewsApi
import com.assignment.data.repository.NewsRepositoryImpl
import com.assignment.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module(includes = [ApiModule::class])
class RepositoryModule {
    @Provides
    fun providePopularRepository(api: INewsApi): NewsRepository {
        return NewsRepositoryImpl(api)
    }
}