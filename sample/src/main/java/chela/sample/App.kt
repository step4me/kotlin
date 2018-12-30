package chela.sample

import android.app.Application
import chela.kotlin.Ch

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Ch(this)
    }
}