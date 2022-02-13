package com.geekbrains.december

import com.geekbrains.december.model.repository.Repository
import com.geekbrains.december.model.repository.RepositoryIpml
import com.geekbrains.december.ui.films.details.DetailsViewModel
import com.geekbrains.december.ui.films.films.FilmsViewModel
import com.geekbrains.december.ui.history.HistoryViewModel
import com.geekbrains.december.ui.maps.MapsViewModel
import com.geekbrains.december.ui.serials.SerialsViewModel
import com.geekbrains.december.ui.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Repository> {
        RepositoryIpml()
    }

    //View models
    viewModel { FilmsViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { SerialsViewModel(get()) }
    viewModel { SettingViewModel(get()) }
    viewModel { HistoryViewModel(get()) }

    viewModel { MapsViewModel(get()) }
}