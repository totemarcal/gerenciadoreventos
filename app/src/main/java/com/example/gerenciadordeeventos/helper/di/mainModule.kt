package com.example.gerenciadordeeventos.helper.di

import com.example.cleanmvvmapp.helper.network.RetrofitService
import com.example.gerenciadordeeventos.data.api.EventApi
import com.example.gerenciadordeeventos.data.repositories.CheckinRepositoryImpl
import com.example.gerenciadordeeventos.data.repositories.EventRepositoryImpl
import com.example.gerenciadordeeventos.domain.repositories.CheckinRepository
import com.example.gerenciadordeeventos.domain.repositories.EventRepository
import com.example.gerenciadordeeventos.domain.usecases.GetEvent
import com.example.gerenciadordeeventos.domain.usecases.GetEventId
import com.example.gerenciadordeeventos.domain.usecases.SetCheckin
import com.example.gerenciadordeeventos.helper.network.NetworkService
import com.example.gerenciadordeeventos.helper.network.NetworkServiceImpl
import com.example.gerenciadordeeventos.presenter.viewmodel.CheckinViewModel
import com.example.gerenciadordeeventos.presenter.viewmodel.EventViewModel
import org.koin.dsl.module

val mainModule = module {

    single { RetrofitService().createService(EventApi::class.java) }

    single<EventRepository> { EventRepositoryImpl(get()) }

    single<CheckinRepository> { CheckinRepositoryImpl(get()) }

    single { GetEvent(get()) }

    single { GetEventId(get()) }

    single { SetCheckin(get()) }

    single<NetworkService> { NetworkServiceImpl() }

    factory { EventViewModel(get(), get(),get()) }

    factory { CheckinViewModel(get(),get()) }

}
