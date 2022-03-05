package com.example.cleanmvvmapp.helper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanmvvmapp.helper.view.ViewAction
import com.example.cleanmvvmapp.helper.view.ViewIntent
import com.example.cleanmvvmapp.helper.view.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<INTENT : ViewIntent, ACTION : ViewAction, STATE : ViewState> :
    ViewModel(),
    IModel<STATE, INTENT> {

    protected val mState = MutableLiveData<STATE>()
    override val state: LiveData<STATE>
        get() {
            return mState
        }

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    final override fun dispatchIntent(intent: INTENT) {
        handleAction(intentToAction(intent))
    }

    abstract fun intentToAction(intent: INTENT): ACTION
    abstract fun handleAction(action: ACTION)
}