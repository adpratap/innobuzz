package com.noreplypratap.innobuzz.di

import android.app.Application
import com.noreplypratap.innobuzz.api.IbServices
import com.noreplypratap.innobuzz.db.DatabaseUsersPosts
import com.noreplypratap.innobuzz.db.UsersPostDao
import com.noreplypratap.innobuzz.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleHilt {

    @Provides
    @Singleton
    fun provideIBService(retrofit: Retrofit): IbServices {
        return retrofit.create(IbServices::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(base_URL: String , okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(base_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Application) : DatabaseUsersPosts {
        return DatabaseUsersPosts.createDatabase(context)
    }

    @Provides
    @Singleton
    fun provideNewsDao(database: DatabaseUsersPosts) : UsersPostDao {
        return database.getPostDao()
    }

    @Provides
    fun provideBaseURL(): String {
        return Constants.BaseURL
    }
}