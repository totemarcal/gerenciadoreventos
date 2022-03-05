package com.example.gerenciadordeeventos.features.event.robot

import androidx.test.rule.ActivityTestRule
import com.example.cleanmvvmapp.test.features.common.ScreenRobot
import com.example.gerenciadordeeventos.R
import com.example.gerenciadordeeventos.presenter.views.activity.MainActivity


class RobotEvent {
    private val robot = ScreenRobot()

    fun launchFirstScreen(testRule: ActivityTestRule<MainActivity>) {
        testRule.launchActivity(null)
    }

    fun verificaLista() {
        robot.waitForIdToAppear(RECYCLER,10)
    }

    fun clicarEmCarroDaLista(pos: Int) {
        robot.clickOnItemList(RECYCLER,pos)
    }

    fun verificaTelaDetalhes() {
        robot.checkViewContainsText("Verificar imagens")
        robot.checkIsDisplayed(IMAGE_EVENT_DETALHES)
    }

    fun voltar() {
        robot.pressBack()
    }

    fun verificarPrimeiraTela() {
        robot.checkViewContainsText("Clean MVVM App")
    }

    companion object {
        private const val RECYCLER = R.id.recycler
        private const val IMAGE_EVENT_DETALHES = R.id.imgEvent
//        private const val BARRA_CARREGAMENTO = R.id.progressBar
    }
}