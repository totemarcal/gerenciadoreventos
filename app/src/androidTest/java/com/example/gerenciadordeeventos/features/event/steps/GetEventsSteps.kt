package com.example.gerenciadordeeventos.features.event.steps
import androidx.test.rule.ActivityTestRule
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

    @E("^a lista de carros aparecer$")
    fun mostrarListaCarro() {
        robot.verificaLista()
    }

    @Quando("clicar em um carro na posição 1")
    fun clicarEmUmcarro() {
        robot.clicarEmCarroDaLista(1)
    }

    @Quando("clicar em um carro na posição 2")
    fun clicarEmUmcarro2() {
        robot.clicarEmCarroDaLista(2)
    }

    @Quando("clicar em um carro na posição 3")
    fun clicarEmUmcarro3() {
        robot.clicarEmCarroDaLista(3)
    }

    @Quando("clicar em um carro na posição 4")
    fun clicarEmUmcarro4() {
        robot.clicarEmCarroDaLista(4)
    }

    @Quando("clicar em um carro na posição 5")
    fun clicarEmUmcarro5() {
        robot.clicarEmCarroDaLista(5)
    }

    @Quando("clicar em um carro na posição 6")
    fun clicarEmUmcarro6() {
        robot.clicarEmCarroDaLista(6)
    }

    @Entao("^vou visualizar os detalhes do carro$")
    fun visualizarLista() {
        robot.verificaTelaDetalhes()
    }
}