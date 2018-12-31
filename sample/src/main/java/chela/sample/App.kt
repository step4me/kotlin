package chela.sample

import android.annotation.SuppressLint
import android.app.Application
import chela.kotlin.Ch

class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic val groupBase = Ch.groupBase()
        @JvmStatic val router = Ch.router(groupBase)
        @JvmStatic val looper = Ch.looper()
        @JvmStatic var style = StyleVM
    }
    override fun onCreate() {
        super.onCreate()
        Ch(this)
    }
}