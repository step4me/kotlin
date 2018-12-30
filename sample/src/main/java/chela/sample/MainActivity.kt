package chela.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import chela.kotlin.Ch
import chela.sample.holder.ContainerHD
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("StaticFieldLeak")
var groupBase = Ch.groupBase()
val router = Ch.router(groupBase)
var looper = Ch.looper()
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        groupBase.group(base_container)
        looper.act(this)
        Ch.waitActivate(this, looper) {
            router.push(ContainerHD, false)
        }
    }
}
