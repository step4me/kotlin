package chela.sample.viewmodel

import chela.kotlin.viewmodel.ChViewModel

object MainVM: ChViewModel() {
    var width = 0.0
    var x = 0.0
    var visible = false
    var fontSize = 15.0

    fun pushed(w: Double) {
        width = w
        x = w
        visible = true
    }
}