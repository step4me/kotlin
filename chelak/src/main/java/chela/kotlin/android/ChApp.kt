package chela.kotlin.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.ClipboardManager
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.*
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources

/**
 * Base object for accessing an application's resource and converting display unit.
 */
object ChApp{
    @JvmStatic lateinit var app: Application
    @JvmStatic lateinit var clip: ClipboardManager
    @JvmStatic lateinit var res: Resources
    @JvmStatic lateinit var asset: AssetManager
    @JvmStatic lateinit var cm: ConnectivityManager
    @JvmStatic lateinit var imm: InputMethodManager
    @JvmStatic lateinit var packName:String
    @JvmStatic lateinit var dm: DisplayMetrics

    @JvmStatic operator fun invoke(a:Application){
        app = a
        res = a.resources
        asset = a.assets
        packName = a.packageName
        imm = app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        clip = app.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        cm = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        dm = res.displayMetrics
        val m = dm.densityDpi.toDouble()
        val d = DisplayMetrics.DENSITY_DEFAULT.toDouble()
        ChWindow.toPx = m / d
        ChWindow.toDp = d / m
        Log.i("ch", "px${ChWindow.toPx} dp${ChWindow.toDp}")
    }
    @JvmStatic fun appVersion():String = app.packageManager.getPackageInfo(app.packageName, 0).versionName

    @JvmStatic fun deviceId() = Settings.Secure.ANDROID_ID
    @JvmStatic fun deviceModel() = Build.MODEL
    @JvmStatic fun deviceVersion() = Build.VERSION.RELEASE
    /**
     * @param type Resource type to find. For example, "string".
     * @param name The name of the desired resource. For example, "app_icon".
     * @return int The associated resource identifier. For example, R.string.app_icon.
     */
    @JvmStatic fun resS2I(type:String, name:String):Int = res.getIdentifier(name, type, packName)
    @JvmStatic fun resDrawable(v: String):Int = resS2I("drawable", v)
    @JvmStatic fun resId(v: String):Int = resS2I("id", v)
    @JvmStatic fun resLayout(v: String):Int = resS2I("layout", v)
    @JvmStatic fun resName(id:Int):String = res.getResourceEntryName(id)
    @JvmStatic fun drawable(v:String):Drawable? = drawable(resS2I("drawable", v))
    @JvmStatic fun drawable(v:Int):Drawable? = AppCompatResources.getDrawable(app, v)
    @JvmStatic fun bitmap2Drawable(v:Bitmap): BitmapDrawable = BitmapDrawable(res, v)
    @JvmStatic fun string(v:String):String = string(resS2I("string", v))
    @JvmStatic fun string(v:Int):String = res.getString(v)
}