package com.lucao.theawesomemovieapp.di.module

import com.lucao.theawesomemovieapp.data.network.MovieService
import com.lucao.theawesomemovieapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(Constants.API_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)
}