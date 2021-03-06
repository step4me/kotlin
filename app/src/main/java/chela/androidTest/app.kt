package chela.androidTest

import android.app.Application
import android.util.Log
import chela.kotlin.Ch
import chela.kotlin.viewmodel.ChViewModel

class User:ChViewModel(){
    var userid = ""
}
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        with(Ch(this)){
            sql.load(asset.string("test.sql"))
            sql.addDb("base", "CH_TEST", 1, "dbCreate,insert", "dbDrop")
            val v = sql["base"]?.select("select", "userid" to "hika" ){User()}
            Log.i("ch","v:" + v!![0].userid)
        }
    }
}