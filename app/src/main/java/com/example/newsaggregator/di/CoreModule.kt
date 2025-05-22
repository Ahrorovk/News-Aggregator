package com.example.newsaggregator.di

import com.example.newsaggregator.core.Constants.BASE_URL
import com.example.newsaggregator.data.rss.RssFeed
import com.example.newsaggregator.data.rss.RssFeedRepositoryImpl
import com.example.newsaggregator.domain.RssFeedRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideRssFeed(): RssFeed = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            XML.asConverterFactory(
                "application/xml; charset=UTF8".toMediaType()
            )
        )
        .client(
            OkHttpClient.Builder().addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
                .build()
        )
        .build()
        .create(RssFeed::class.java)

    @Provides
    @Singleton
    fun provideRssFeedRepository(rssFeed: RssFeed): RssFeedRepository =
        RssFeedRepositoryImpl(rssFeed)
}