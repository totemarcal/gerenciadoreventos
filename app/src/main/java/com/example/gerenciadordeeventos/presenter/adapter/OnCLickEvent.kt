package com.example.gerenciadordeeventos.presenter.adapter

import com.example.gerenciadordeeventos.presenter.model.EventUiModel


interface OnCLickEvent {
    fun onClickEvent(eventUiModel: EventUiModel)
}