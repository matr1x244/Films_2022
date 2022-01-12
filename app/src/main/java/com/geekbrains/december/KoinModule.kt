package com.geekbrains.december

import com.geekbrains.december.model.repository.Repository
import com.geekbrains.december.model.repository.RepositoryIpml
import com.geekbrains.december.ui.films.FilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Repository> {
        RepositoryIpml()
    }

    //View models
    viewModel {
        FilmsViewModel(get()) }
}