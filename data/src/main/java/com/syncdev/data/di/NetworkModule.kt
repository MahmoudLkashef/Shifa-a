package com.syncdev.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.syncdev.data.remote.ApiService
import com.syncdev.data.remote.data_source.RemoteDataSourceImp
import com.syncdev.domain.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * A Dagger module for network-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides an instance of [OkHttpClient] with a 10 second connection timeout and read timeout.
     * @return An instance of OkHttpClient.
     */
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Provides an instance of [Retrofit] with a base URL, the OkHttpClient instance, and a GsonConverterFactory for JSON parsing.
     * @param okHttpClient An instance of OkHttpClient.
     * @return An instance of Retrofit.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides an instance of the [ApiService] interface using the Retrofit instance.
     * @param retrofit An instance of Retrofit.
     * @return An instance of ApiService.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Provides an instance of the [FirebaseDatabase] singleton.
     * @return An instance of FirebaseDatabase.
     */
    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    /**
     * Provides an instance of the [FirebaseAuth] singleton.
     * @return An instance of FirebaseAuth.
     */
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteBindsModule {

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImp: RemoteDataSourceImp): RemoteDataSource

}
