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
import chela.kotlin.viewmodel.scanner.ChScanner
import chela.sample.*
import chela.sample.data.Game
import chela.sample.viewmodel.MainVM
import chela.sample.viewmodel.GameListVM
import java.lang.Exception

@SuppressLint("StaticFieldLeak")
object MainHD : ChHolder<View>() {
    @JvmStatic
    private lateinit var rv: RecyclerView
    // TODO : convert to json
    @JvmStatic
    private val items = listOf(
        Game(0, 0, 1, 20),
        Game(1, 1, 2, 30)
    )
    @JvmStatic
    private lateinit var scan: ChScanner.Scanned
    private val main = MainVM
    override fun create(base: ChHolderBase<View>): View {
        if (base !is ChGroupBase) throw Exception("invalid base:$base")
        val v = base.inflate(R.layout.main)
        rv = v.findViewById(R.id.main_rv)
        scan = Ch.scanner.scan(this, v)
        return scan.render()
    }

    override fun push(base: ChHolderBase<View>, isRestore: Boolean) {
        main.pushed(App.groupBase.group.width.toDouble())
        if (isRestore) {
            App.router.unlockPush()
            main.x = 0.0
            Ch.scanner[this]?.render()
        } else {
            App.looper.invoke(ChLooper.Item.Time(700)) {
                main.x = it.circleInOut(main.width, 0.0)
                Ch.scanner[this]?.renderSync()
                with(rv) {
                    adapter = ItemAdapter(items)
                    layoutManager = LinearLayoutManager(Ch.app.app)
                }
            }.next(ChLooper.Item.Time(700), ChLooper.Item.Ended { App.router.unlockPush() }) {
                main.fontSize = it.bounceOut(15.0, 60.0)
                Ch.scanner[this]?.renderSync()
            }
        }
    }

    private class ItemAdapter(val items: List<Game>) : RecyclerView.Adapter<ItemHD>() {
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

    private class ItemHD(private val view: View) : RecyclerView.ViewHolder(view) {
        private val gameListVM = GameListVM

        fun bind(data: Game) {
            gameListVM.pushed(data)
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    gameListVM.clickedItem = MainHD.items[position]
                    App.looper(App.style.popTime) { item ->
                        main.x = App.style.popX(item)
                        scan.renderSync()
                    }.next(App.style.pushTime, Ch.ended { App.router.push(GameHD, false) })
                }
            }
            Ch.scanner.scan(this, view).render()
        }
    }
}

