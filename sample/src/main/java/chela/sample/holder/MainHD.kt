package chela.sample.holder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chela.kotlin.Ch
import chela.kotlin.looper.ChLooper
import chela.kotlin.viewmodel.holder.ChGroupBase
import chela.kotlin.viewmodel.holder.ChHolder
import chela.kotlin.viewmodel.holder.ChHolderBase
import chela.sample.R
import chela.sample.data.Game
import chela.sample.groupBase
import chela.sample.looper
import chela.sample.router
import chela.sample.viewmodel.MainVM
import chela.sample.viewmodel.GameLevelVM
import java.lang.Exception

@SuppressLint("StaticFieldLeak")
object ContainerHD: ChHolder<View>() {
    private val container = MainVM
    private lateinit var rv: RecyclerView
    override fun create(base: ChHolderBase<View>): View {
        if (base !is ChGroupBase) throw Exception("invalid base:$base")
        val v = base.inflate(R.layout.main)
        rv = v.findViewById(R.id.main_rv)
        return Ch.scanner.scan(this, v).render()
    }

    override fun push(base: ChHolderBase<View>, isRestore: Boolean) {
        container.pushed(groupBase.group.width.toDouble())
        if (isRestore) {
            router.unlockPush()
            container.x = 0.0
            Ch.scanner[this]?.render()
        } else {
            looper.invoke(ChLooper.Item.Time(700)) {
                container.x = it.circleInOut(container.width, 0.0)
                Ch.scanner[this]?.renderSync()
                with(rv) {
                    adapter = ItemAdapter(
                        listOf(
                            Game(0, 0, 1, 20),
                            Game(1, 1, 0, 30)
                        ))
                    layoutManager = LinearLayoutManager(Ch.app.app)
                }
            }.next(ChLooper.Item.Time(700), ChLooper.Item.Ended{router.unlockPush()}) {
                container.fontSize = it.bounceOut(15.0, 60.0)
                Ch.scanner[this]?.renderSync()
            }
        }
    }
}

private class ItemAdapter(val items: List<Game>): RecyclerView.Adapter<ItemHD>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHD {
        return ItemHD(LayoutInflater.from(parent.context).inflate(R.layout.item_game_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemHD, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

private class ItemHD(private val view: View): RecyclerView.ViewHolder(view) {
    private val gameLevelVM = GameLevelVM

    fun bind(data: Game) {
        gameLevelVM.pushed(data)
        Ch.scanner.scan(this, view).render()
    }
}