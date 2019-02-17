package com.bobbyprabowo.android.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this, MainViewModelProvider()).get(MainViewModel::class.java)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter()
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()


        compositeDisposable.add(
            mainViewModel.getState()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::render)
        )

        val intentsObservable = Observable.merge(adapter.getItemClickObservable(), Observable.just(MainIntent.InitialIntent))
        mainViewModel.processIntent(intentsObservable)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun render(state: MainState) {
        adapter.setData(state.data)
        adapter.notifyDataSetChanged()
    }
}
