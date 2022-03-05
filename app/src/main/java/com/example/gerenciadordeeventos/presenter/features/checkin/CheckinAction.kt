package com.example.cleanmvvmapp.presenter.mvi.features.carro

import com.example.cleanmvvmapp.helper.view.ViewAction

sealed class CheckinAction : ViewAction {
    object SetCheckin : CheckinAction()
}