package chela.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chela.kotlin.Ch
import chela.sample.holder.SplashHD
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.groupBase.group(root_main)
        App.looper.act(this)
        Ch.waitActivate(this, App.looper) {
            App.router.push(SplashHD, false)
        }
    }
}
