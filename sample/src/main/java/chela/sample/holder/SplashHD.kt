package chela.sample.holder

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import chela.kotlin.Ch
import chela.kotlin.viewmodel.holder.ChGroupBase
import chela.kotlin.viewmodel.holder.ChHolder
import chela.kotlin.viewmodel.holder.ChHolderBase
import chela.kotlin.viewmodel.scanner.ChScanner
import chela.sample.App
import chela.sample.R
import chela.sample.viewmodel.SplashVM

object SplashHD: ChHolder<View>() {
    @SuppressLint("StaticFieldLeak")
    @JvmStatic private lateinit var scan: ChScanner.Scanned
    override fun create(base: ChHolderBase<View>): View {
        if(base !is ChGroupBase) throw Exception("invalid base:$base")
        SplashVM.start()
        scan = Ch.scanner.scan(this, base.inflate(R.layout.splash))
        return scan.render()
    }

    override fun resume(base: ChHolderBase<View>, isRestore: Boolean) {
        if (isRestore) {
            App.router.unlockPush()
            SplashVM.end()
            scan.render()
            start()
        } else {
            App.looper(Ch.time(500)) {
                Log.e("splash", "500")
                SplashVM.back = it.circleOut(0.0, 1.0)
                scan.renderSync()
            }.next(Ch.time(700), Ch.ended { App.router.unlockPush() }) {
                Log.e("splash", "700")
                SplashVM.title = it.circleOut(0.0, 1.0)
                SplashVM.margin = it.circleOut(50.0, 0.0).toInt()
                SplashVM.sx = it.circleOut(0.8, 1.0)
                SplashVM.sy = it.circleOut(0.8, 1.0)
                scan.renderSync()
            }.next(Ch.time(300), Ch.ended { start() }) {
                Log.e("splash", "300")
            }
        }
    }

    override fun pause(base: ChHolderBase<View>, isJump: Boolean) {
        App.looper(App.style.popTime) {
            SplashVM.base = App.style.popAlpha(it)
            SplashVM.x = App.style.popX(it)
            scan.renderSync()
        }
    }

    private fun start() {
        App.router.push(MainHD, false)
    }
}