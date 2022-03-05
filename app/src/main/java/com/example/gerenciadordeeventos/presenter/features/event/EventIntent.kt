package com.example.cleanmvvmapp.presenter.mvi.features.carro

import com.example.cleanmvvmapp.helper.view.ViewIntent

sealed class EventIntent : ViewIntent {
    object LoadAllEvents : EventIntent()
    object LoadEventId : EventIntent()
}