package chela.sample.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import chela.kotlin.Ch
import chela.kotlin.viewmodel.ChViewModel

object GameVM: ChViewModel() {
    private var asset: ByteArray? = null
    var puzzleBitmap: Bitmap? = null

    fun pushed(resPath: String) {
        asset = Ch.asset.bytes(resPath)
        asset?.let {
            puzzleBitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
        }
    }

}