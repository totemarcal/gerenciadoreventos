package com.example.gerenciadordeeventos.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanmvvmapp.data.exception.NetworkException
import com.example.cleanmvvmapp.helper.viewmodel.BaseViewModel
import com.example.cleanmvvmapp.presenter.mvi.features.carro.EventAction
import com.example.cleanmvvmapp.presenter.mvi.features.carro.EventIntent
import com.example.cleanmvvmapp.presenter.mvi.features.carro.EventState
import com.example.gerenciadordeeventos.domain.usecases.GetEvent
import com.example.gerenciadordeeventos.helper.ResultX
import com.example.gerenciadordeeventos.helper.network.NetworkService
import com.example.gerenciadordeeventos.presenter.model.toUiModel
import kotlinx.coroutines.launch

class EventViewModel(
    private val getEvent: GetEvent,
    private val networkService : NetworkService
) : BaseViewModel<EventIntent, EventAction, EventState>()  {

    val liveData: LiveData<EventState> get() = mState

    override fun intentToAction(intent: EventIntent): EventAction {
        return when (intent) {
            is EventIntent.LoadAllEvents -> EventAction.AllEvents
        }
    }

    override fun handleAction(action: EventAction) {
        launchOnUI {
            when (action) {
                is EventAction.AllEvents -> {
                    fetchEvents();
                }
            }
        }
    }

    fun fetchEvents() {
        viewModelScope.launch {
           if( networkService.isNetworkAvailable()) {
                mState.value = EventState.Loading(true)

                val result = getEvent()
                mState.value = EventState.Loading(false)

                when (result) {
                    is ResultX.Success -> {
                        mState.value = EventState.ResultAllEvents(result.value.map { carro ->
                            carro.toUiModel()
                        })
                    }
                    is ResultX.Failure -> {
                        mState.value = EventState.Error(result.error.toString(),result.error)
                    }
                }
            } else {
                mState.value = EventState.Error(NetworkException().message)
            }
        }
    }
}