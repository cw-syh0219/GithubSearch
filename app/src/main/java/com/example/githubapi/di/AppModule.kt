package com.example.githubapi.di

import android.content.Context
import com.example.githubapi.data.local.AppDatabase
import com.example.githubapi.data.local.RepositoryDao
import com.example.githubapi.data.remote.GithubRemoteDataSource
import com.example.githubapi.data.remote.GithubService
import com.example.githubapi.data.repository.GithubRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
        }.build())
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideGithubService(retrofit: Retrofit): GithubService =
        retrofit.create(GithubService::class.java)

//    @Singleton
//    @Provides
//    fun provideGithubRemoteDataSource(githubService: GithubService) =
//        GithubRemoteDataSource(githubService)

    @Singleton
    @Provides
    fun provideRepository(
        githubService: GithubService,
        githubLocalDataSource: RepositoryDao
    ) = GithubRepository(githubService, githubLocalDataSource)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.repositoryDao()
}