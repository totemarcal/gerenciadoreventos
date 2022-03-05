package com.example.cleanmvvmapp.presenter.mvi.features.carro

import com.example.cleanmvvmapp.helper.view.ViewState
import com.example.gerenciadordeeventos.presenter.model.EventUiModel

sealed class EventState : ViewState{
    open class Loading(val loading: Boolean) : EventState()
    data class ResultAllEvents(val data : List<EventUiModel>): EventState()
    data class Error(val msg: String, val error: Throwable? = null) : EventState()
}