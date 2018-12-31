package chela.sample.viewmodel

import chela.kotlin.viewmodel.ChViewModel

object SplashVM: ChViewModel() {
    fun start(){
        x = 0.0
        base = 1.0
        title = 0.0
        back = 0.0
        margin = 50
        sx = 0.8
        sy = 0.8
    }
    fun end() {
        title = 1.0
        back = 1.0
        margin = 0
        sx = 1.0
        sy = 1.0
    }
    var x = 0.0
    var base = 1.0
    var title = 0.0
    var back = 0.0
    var sx = 0.8
    var sy = 0.8
    var margin = 50
}