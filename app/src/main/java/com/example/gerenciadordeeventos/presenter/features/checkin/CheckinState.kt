package com.example.cleanmvvmapp.presenter.mvi.features.carro

import com.example.cleanmvvmapp.helper.view.ViewState
import com.example.gerenciadordeeventos.presenter.model.CheckinUiModel
import com.example.gerenciadordeeventos.presenter.model.EventUiModel

sealed class CheckinState : ViewState{
    open class Loading(val loading: Boolean) : CheckinState()
    data class SetCheckin(val data : Boolean): CheckinState()
    data class Error(val msg: String, val error: Throwable? = null) : CheckinState()
}