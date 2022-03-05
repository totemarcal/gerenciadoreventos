package com.example.gerenciadordeeventos.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanmvvmapp.data.exception.NetworkException
import com.example.cleanmvvmapp.helper.viewmodel.BaseViewModel
import com.example.cleanmvvmapp.presenter.mvi.features.carro.EventAction
import com.example.cleanmvvmapp.presenter.mvi.features.carro.EventIntent
import com.example.cleanmvvmapp.presenter.mvi.features.carro.EventState
import com.example.gerenciadordeeventos.domain.usecases.GetEvent
import com.example.gerenciadordeeventos.domain.usecases.GetEventId
import com.example.gerenciadordeeventos.helper.ResultX
import com.example.gerenciadordeeventos.helper.network.NetworkService
import com.example.gerenciadordeeventos.presenter.model.toUiModel
import kotlinx.coroutines.launch

class EventViewModel(
    private val getEvent: GetEvent,
    private val getEventId: GetEventId,
    private val networkService : NetworkService
) : BaseViewModel<EventIntent, EventAction, EventState>()  {

    val liveData: LiveData<EventState> get() = mState
    var id: String = ""

    @JvmName("setId1")
    fun setId(id: String){
        this.id=id
        getEventId.setId(id)
    }
    override fun intentToAction(intent: EventIntent): EventAction {
        return when (intent) {
            is EventIntent.LoadAllEvents -> EventAction.AllEvents
            is EventIntent.LoadEventId -> EventAction.EventId
        }
    }

    override fun handleAction(action: EventAction) {
        launchOnUI {
            when (action) {
                is EventAction.AllEvents -> {
                    fetchEvents();
                }
                is EventAction.EventId -> {
                    fetchEventId();
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
                        mState.value = EventState.ResultAllEvents(result.value.map { event ->
                            event.toUiModel()
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

    fun fetchEventId() {
        viewModelScope.launch {
            if( networkService.isNetworkAvailable()) {
                mState.value = EventState.Loading(true)

                val result = getEventId()
                mState.value = EventState.Loading(false)

                when (result) {
                    is ResultX.Success -> {
                        mState.value = EventState.ResultEventId(result.value.toUiModel())
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