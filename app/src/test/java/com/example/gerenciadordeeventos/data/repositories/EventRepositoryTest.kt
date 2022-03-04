package com.example.gerenciadordeeventos.data.repositories

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cleanmvvmapp.data.exception.NotFoundException
import com.example.cleanmvvmapp.data.exception.ServerException
import com.example.gerenciadordeeventos.data.api.EventApi
import com.example.gerenciadordeeventos.data.model.EventResponse
import com.example.gerenciadordeeventos.data.model.toEvent
import com.example.gerenciadordeeventos.helper.ResultX
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class EventRepositoryTest {
    private lateinit var rep: EventRepositoryImpl
    private lateinit var eventApi: EventApi
    private lateinit var responseEvent : Response<List<EventResponse>>

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        eventApi = mockk()
        responseEvent = mockk()
        rep = EventRepositoryImpl(eventApi)
    }

    @Test
    fun testGetCarros () = runBlocking {
        // arrange
        val listResponse = listOf(
            EventResponse("1","http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png","Feira de adoção de animais na Redenção","29.99"))
        val response = Response.success(listResponse)
        val listCarros = listResponse.map { it.toEvent() }
        coEvery { eventApi.getEvent() } returns response
        //act
        val result = rep.getEvent()
        //assert
        assertTrue(result is ResultX.Success)
        assertEquals(result.getOrNull(), listCarros)
    }

    @Test
    fun test404PegarListaCarros () = runBlocking {
        // arrange
        every { responseEvent.isSuccessful } returns false
        coEvery { eventApi.getEvent() } returns Response.error(404, ResponseBody.create(null,""))
        //act
        val result = rep.getEvent()
        //assert
        Assert.assertTrue(result is ResultX.Failure)
        Assert.assertTrue(result.exceptionOrNull() is NotFoundException)
    }

    @Test
    fun test500PegarListaCarros () = runBlocking {
        // arrange
        every { responseEvent.isSuccessful } returns false
        coEvery { eventApi.getEvent() } returns Response.error(500, ResponseBody.create(null,""))
        //act
        val result = rep.getEvent()
        //assert
        Assert.assertTrue(result is ResultX.Failure)
        Assert.assertTrue(result.exceptionOrNull() is ServerException)
    }

}