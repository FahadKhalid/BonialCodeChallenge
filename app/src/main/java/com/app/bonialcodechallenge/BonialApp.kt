package com.app.bonialcodechallenge

import android.app.Application
import com.app.bonialcodechallenge.di.networkModule
import com.app.bonialcodechallenge.di.repositoryModule
import com.app.bonialcodechallenge.di.useCaseModule
import com.app.bonialcodechallenge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BonialApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BonialApp)
            modules(listOf(networkModule, repositoryModule, useCaseModule, viewModelModule))
        }
    }
}