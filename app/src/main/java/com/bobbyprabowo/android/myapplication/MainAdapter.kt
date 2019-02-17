package com.bobbyprabowo.android.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainAdapter: RecyclerView.Adapter<MainAdapter.ItemViewHolder>() {

    private val itemClickSubject: PublishSubject<MainIntent.AdapterClickIntent> = PublishSubject.create()
    private val dataList = mutableListOf<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_data, null, false)
        val viewHolder = ItemViewHolder(layout)
        layout.setOnClickListener {
            itemClickSubject.onNext(MainIntent.AdapterClickIntent(viewHolder.key))
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.setData(dataList[position])
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var key:String = ""
        private val titleTextView = itemView.findViewById<TextView>(R.id.text_title)
        private val progressTextView = itemView.findViewById<TextView>(R.id.text_progress)
        fun setData(data: Data) {
            key = data.name
            titleTextView.text = data.name
            progressTextView.text = data.progress.toString()
        }
    }

    fun getItemClickObservable(): Observable<MainIntent> {
        return itemClickSubject as Observable<MainIntent>
    }

    fun setData(map: Map<String, Data>) {
        dataList.clear()
        dataList.addAll(map.values)
    }
}
