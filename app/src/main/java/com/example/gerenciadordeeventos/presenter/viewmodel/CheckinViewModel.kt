package com.example.gerenciadordeeventos.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanmvvmapp.data.exception.NetworkException
import com.example.cleanmvvmapp.helper.viewmodel.BaseViewModel
import com.example.cleanmvvmapp.presenter.mvi.features.carro.*
import com.example.gerenciadordeeventos.domain.usecases.GetEvent
import com.example.gerenciadordeeventos.domain.usecases.GetEventId
import com.example.gerenciadordeeventos.domain.usecases.SetCheckin
import com.example.gerenciadordeeventos.helper.ResultX
import com.example.gerenciadordeeventos.helper.network.NetworkService
import com.example.gerenciadordeeventos.presenter.model.CheckinUiModel
import com.example.gerenciadordeeventos.presenter.model.toCheckin
import com.example.gerenciadordeeventos.presenter.model.toUiModel
import kotlinx.coroutines.launch

class CheckinViewModel(
    private val setCheckin: SetCheckin,
    private val networkService : NetworkService
) : BaseViewModel<CheckinIntent, CheckinAction, CheckinState>()  {

    val liveData: LiveData<CheckinState> get() = mState

    lateinit var checkin : CheckinUiModel

    @JvmName("setCheckin1")
    fun setCheckin(checkin : CheckinUiModel){
        this.checkin=checkin
        setCheckin.setCheckin(checkin.toCheckin())
    }

    override fun intentToAction(intent: CheckinIntent): CheckinAction {
        return when (intent) {
            is CheckinIntent.SetCheckin -> CheckinAction.SetCheckin
        }
    }

    override fun handleAction(action: CheckinAction) {
        launchOnUI {
            when (action) {
                is CheckinAction.SetCheckin -> {
                    makeCheckin();
                }
            }
        }
    }

    fun makeCheckin() {
        viewModelScope.launch {
           if( networkService.isNetworkAvailable()) {
                mState.value = CheckinState.Loading(true)

                val result = setCheckin()
                mState.value = CheckinState.Loading(false)

                when (result) {
                    is ResultX.Success -> {
                        mState.value = CheckinState.SetCheckin(result.value)
                    }
                    is ResultX.Failure -> {
                        mState.value = CheckinState.Error(result.error.toString(),result.error)
                    }
                }
            } else {
                mState.value = CheckinState.Error(NetworkException().message)
            }
        }
    }
}