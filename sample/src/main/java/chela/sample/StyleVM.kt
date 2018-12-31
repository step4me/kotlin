package chela.sample

import chela.kotlin.Ch
import chela.kotlin.looper.ChItem
import chela.kotlin.viewmodel.ChViewModel

object StyleVM: ChViewModel() {
    @JvmStatic val pushTime = Ch.time(350)
    @JvmStatic fun pushAlpha(it: ChItem): Double = it.circleOut(0.5, 1.0)
    @JvmStatic fun pushX(it: ChItem): Double = it.circleOut(Ch.window.width.toDouble(), 0.0)
    @JvmStatic val popTime = Ch.time(320)
    @JvmStatic fun popAlpha(it: ChItem): Double = it.circleOut(1.0, 0.5)
    @JvmStatic fun popX(it: ChItem): Double = it.circleOut(0.0, -Ch.window.width.toDouble())
    @JvmStatic val actionBar = "#18ba9b"
}