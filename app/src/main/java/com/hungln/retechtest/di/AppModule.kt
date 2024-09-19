package com.hungln.retechtest.di

import com.hungln.retechtest.data.network.ApiService
import com.hungln.retechtest.data.source.UserRepository
import com.hungln.retechtest.data.source.local.AppLocalDatabase
import com.hungln.retechtest.data.source.local.LocalDataSource
import com.hungln.retechtest.data.source.remote.RemoteDataSource
import com.hungln.retechtest.ui.userdetail.UserDetailViewModel
import com.hungln.retechtest.ui.userlist.UserListViewModel
import com.hungln.retechtest.util.AppConstants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(ApiService::class.java) }
}

val databaseModule = module {
    single { AppLocalDatabase.getDatabase(androidContext()).userDao() }
    single { AppLocalDatabase.getDatabase(androidContext()).userRepositoryDao() }
}

val dataSourceModule = module {
    single { RemoteDataSource(get()) }
}

val localDataSourceModule = module {
    single { LocalDataSource(get(), get()) }
}

val repositoryModule = module {
    single { UserRepository(get(), get()) }
}

val userListViewModel = module {
    viewModel { UserListViewModel(get()) }
}

val userDetailViewModel = module {
    viewModel { UserDetailViewModel(get()) }
}
