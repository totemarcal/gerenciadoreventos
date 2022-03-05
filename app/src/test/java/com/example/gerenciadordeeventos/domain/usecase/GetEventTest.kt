package com.example.gerenciadordeeventos.domain.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gerenciadordeeventos.data.exception.GetEventException
import com.example.gerenciadordeeventos.data.model.EventResponse
import com.example.gerenciadordeeventos.data.model.toEvent
import com.example.gerenciadordeeventos.domain.model.Event
import com.example.gerenciadordeeventos.domain.repositories.EventRepository
import com.example.gerenciadordeeventos.domain.usecases.GetEvent
import com.example.gerenciadordeeventos.helper.ResultX
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetEventTest {
    private lateinit var getC: GetEvent
    private lateinit var mockRepository: EventRepository

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockRepository = mockk()
        getC = GetEvent(mockRepository)
    }

    @Test
    fun testPegarListaEvents () = runBlocking {
        val listResponse = listOf(
            EventResponse("1","http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png","Feira de adoção de animais na Redenção","29.99")
        )
        val resultX : ResultX<List<Event>> = ResultX.Success(listResponse.map { it.toEvent() })
        coEvery { mockRepository.getEvent() } returns resultX
        //act
        val carros = getC.invoke()
        //assert
        assertTrue(carros is ResultX.Success)
        assertEquals(carros,resultX)
    }

    @Test
    fun testErroPegarListaEvent () = runBlocking {
        val result = ResultX.Failure<List<Event>>(GetEventException())
        coEvery { mockRepository.getEvent() } returns result
        //  act
        val carros = getC.invoke()
        //assert
        assertEquals(carros,result)
    }
}