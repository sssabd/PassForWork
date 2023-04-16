package com.example.passforwork.core.di

import com.example.passforwork.core.base.network.ApiService
import com.example.passforwork.core.base.network.RetrofitProvider
import com.example.passforwork.features.registration.presentation.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myAppModules = module {

    single<ApiService> { RetrofitProvider().service }


}

val viewModelModules = module {

    viewModel {
        RegistrationViewModel(get())
    }
}