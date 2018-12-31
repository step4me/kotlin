package chela.sample.viewmodel

import android.util.Log
import chela.kotlin.Ch
import chela.kotlin.viewmodel.ChViewModel
import chela.sample.R
import chela.sample.data.Game

object GameListVM: ChViewModel() {
    var mode = "0"
    var currentStage = 0
    var maxStage = 0
    var stageLevel = ""
    var fontSize = 15.0
    var clickedItem = Game()

    fun pushed(game: Game) {
        mode = game.mode.toString()
        currentStage = game.currentStage
        maxStage = game.maxStage
        stageLevel = Ch.app.res.getString(R.string.current_stage_level, currentStage, maxStage)
    }

}