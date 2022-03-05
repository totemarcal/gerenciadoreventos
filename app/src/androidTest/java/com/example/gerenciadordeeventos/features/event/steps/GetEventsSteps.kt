package com.example.gerenciadordeeventos.features.event.steps
import android.support.test.rule.ActivityTestRule
import com.example.gerenciadordeeventos.features.event.robot.RobotEvent
import com.example.gerenciadordeeventos.presenter.viewmodel.EventViewModel
import com.example.gerenciadordeeventos.presenter.views.activity.MainActivity
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.pt.Dado
import cucumber.api.java.pt.E
import cucumber.api.java.pt.Entao
import cucumber.api.java.pt.Quando

class GetEventsSteps {
    private val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    private val robot = RobotEvent()

    private lateinit var viewModel: EventViewModel

    @Before
    fun setup() {
//        viewModel = mockk()
//        coEvery { viewModel.liveData }
        robot.launchFirstScreen(activityRule)
    }

    @After
    fun tearDown() {
//        robot.voltar()
    }

    @Dado("^que estou na primeira tela do app$")
    fun abrirPrimeiraTela() {
        robot.verificarPrimeiraTela()
    }

    @E("^a lista de Events aparecer$")
    fun mostrarListaEvent() {
        robot.verificaLista()
    }

    @Quando("clicar em um Event na posição 1")
    fun clicarEmUmEvent() {
        robot.clicarEmEventDaLista(1)
    }

    @Quando("clicar em um Event na posição 2")
    fun clicarEmUmEvent2() {
        robot.clicarEmEventDaLista(2)
    }

    @Quando("clicar em um Event na posição 3")
    fun clicarEmUmEvent3() {
        robot.clicarEmEventDaLista(3)
    }

    @Quando("clicar em um Event na posição 4")
    fun clicarEmUmEvent4() {
        robot.clicarEmEventDaLista(4)
    }

    @Quando("clicar em um Event na posição 5")
    fun clicarEmUmEvent5() {
        robot.clicarEmEventDaLista(5)
    }

    @Quando("clicar em um Event na posição 6")
    fun clicarEmUmEvent6() {
        robot.clicarEmEventDaLista(6)
    }

    @Entao("^vou visualizar os detalhes do Event$")
    fun visualizarLista() {
        robot.verificaTelaDetalhes()
    }
}