package com.example.gerenciadordeeventos.presenter.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.cleanmvvmapp.presenter.mvi.features.carro.EventState
import com.example.gerenciadordeeventos.EventApplication
import com.example.gerenciadordeeventos.data.exception.GetEventException
import com.example.gerenciadordeeventos.data.model.EventResponse
import com.example.gerenciadordeeventos.data.model.toEvent
import com.example.gerenciadordeeventos.domain.model.Event
import com.example.gerenciadordeeventos.domain.usecases.GetEvent
import com.example.gerenciadordeeventos.helper.ResultX
import com.example.gerenciadordeeventos.helper.network.NetworkService
import com.example.gerenciadordeeventos.presenter.model.EventUiModel
import com.example.gerenciadordeeventos.presenter.model.toUiModel
import com.example.gerenciadordeeventos.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventsViewModelTest {
    private lateinit var viewmodel: EventViewModel
    private lateinit var getEventUseCaseMock: GetEvent
    private lateinit var networkService: NetworkService
    private lateinit var eventApplication: EventApplication

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var observer: Observer<EventState>

    @Before
    fun setup() {
        getEventUseCaseMock = mockk()
        eventApplication = mockk()
        networkService = mockk()
        viewmodel = EventViewModel(getEventUseCaseMock,networkService)
        observer = mockk()
    }

    @After
    fun tearDown(){
    }

    @Test
    fun testSucessoListaDeCarros() {
        // arrange
        val mockEventsList = listOf(
            EventResponse("1",
                "http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png",
                "Feira de adoção de animais na Redenção",
                "29.99").toEvent()
        )
        val mockEventsListUi = mockEventsList.map {
            it.toUiModel()
        }

        val result = ResultX.Success(mockEventsList)

        every { observer.onChanged(any()) }.returns(Unit)
        every { networkService.isNetworkAvailable() }.returns(true)
        coEvery { getEventUseCaseMock.invoke() } returns result
        viewmodel.liveData.observeForever(observer)

        // Invoke
        runBlocking { viewmodel.fetchEvents() }

        // Verify
        verifyOrder {
            observer.onChanged(EventState.Loading(true))
            observer.onChanged(EventState.Loading(false))
            observer.onChanged(EventState.ResultAllEvents(mockEventsListUi))
        }
    }

    @Test
    fun testErroListaDeCarros() {
        // arrange
        val result = ResultX.Failure<List<Event>>(GetEventException())
        every { observer.onChanged(any()) }.returns(Unit)
        every { networkService.isNetworkAvailable() }.returns(true)
        coEvery { getEventUseCaseMock() } returns result
        viewmodel.liveData.observeForever(observer)

        // act
        runBlocking { viewmodel.fetchEvents() }

        // assert
        verifyOrder {
            observer.onChanged(EventState.Loading(true))
            observer.onChanged(EventState.Loading(false))
            observer.onChanged(EventState.Error(result.exceptionOrNull().toString(),
                result.exceptionOrNull()!!
            ))
        }
    }

    @Test
    fun testNetworkError() {
        every { observer.onChanged(any()) }.returns(Unit)
        every { networkService.isNetworkAvailable() }.returns(false)
        viewmodel.liveData.observeForever(observer)
        runBlocking { viewmodel.fetchEvents() }
        verifyOrder {
            observer.onChanged(EventState.Error("Internet indisponível"))
        }
    }
}