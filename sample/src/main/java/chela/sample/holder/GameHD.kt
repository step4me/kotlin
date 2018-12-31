package chela.sample.holder

import android.view.View
import chela.kotlin.Ch
import chela.kotlin.viewmodel.holder.ChGroupBase
import chela.kotlin.viewmodel.holder.ChHolder
import chela.kotlin.viewmodel.holder.ChHolderBase
import chela.sample.App
import chela.sample.R
import chela.sample.viewmodel.GameListVM
import chela.sample.viewmodel.GameVM
import java.lang.Exception

object GameHD: ChHolder<View>() {
    private val game = GameVM
    override fun create(base: ChHolderBase<View>): View {
        if (base !is ChGroupBase) throw Exception("invalid base:$base")
        return Ch.scanner.scan(this, base.inflate(R.layout.game)).render()
    }

    override fun resume(base: ChHolderBase<View>, isRestore: Boolean) {
        // TODO : convert to json data
        when (GameListVM.clickedItem.currentStage) {
            1 -> GameVM.pushed("puzzle/test_shape.png")
            2 -> GameVM.pushed("puzzle/test_shape2.png")
        }
        if (isRestore) {
            App.router.unlockPush()
            Ch.scanner[this]?.render()
        } else {
            App.looper(App.style.pushTime) {
                Ch.scanner[this]?.render()
            }
        }
    }
}