package com.app.bonialcodechallenge.di

import com.app.bonialcodechallenge.constants.ApiConstants.getBaseUrl
import com.app.bonialcodechallenge.data.remote.ApiService
import com.app.bonialcodechallenge.domain.repository.ContentRepository
import com.app.bonialcodechallenge.domain.repository.ContentRepositoryImpl
import com.app.bonialcodechallenge.domain.usecases.GetContentUseCase
import com.app.bonialcodechallenge.presentation.viewmodel.ContentViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// ✅ Network Module
val networkModule = module {
    // Moshi for JSON Parsing
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    // Logging Interceptor
    factory {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // OkHttpClient with Interceptor
    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(get()) // Inject OkHttpClient
            .addConverterFactory(MoshiConverterFactory.create(get())) // Inject Moshi
            .build()
    }

    // ApiService
    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}

// ✅ Repository Module
val repositoryModule = module {
    single<ContentRepository> { ContentRepositoryImpl(get()) } // Inject ApiService
}

// ✅ Use Case Module
val useCaseModule = module {
    factory { GetContentUseCase(get()) } // Inject Repository
}

// ✅ ViewModel Module
val viewModelModule = module {
    viewModel { ContentViewModel(get()) } // Inject UseCase
}