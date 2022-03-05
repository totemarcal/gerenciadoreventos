package com.example.cleanmvvmapp.presenter.mvi.features.carro

import com.example.cleanmvvmapp.helper.view.ViewIntent

sealed class CheckinIntent : ViewIntent {
    object SetCheckin : CheckinIntent()
}