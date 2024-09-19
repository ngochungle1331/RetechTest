package com.hungln.retechtest

import android.app.Application
import com.hungln.retechtest.di.dataSourceModule
import com.hungln.retechtest.di.databaseModule
import com.hungln.retechtest.di.localDataSourceModule
import com.hungln.retechtest.di.networkModule
import com.hungln.retechtest.di.repositoryModule
import com.hungln.retechtest.di.userDetailViewModel
import com.hungln.retechtest.di.userListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UserListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@UserListApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    dataSourceModule,
                    localDataSourceModule,
                    repositoryModule,
                    userListViewModel,
                    userDetailViewModel,
                )
            )
        }
    }
}