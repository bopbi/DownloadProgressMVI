package com.bobbyprabowo.android.myapplication

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainViewModel(private val getData: GetData, private val startProgress: StartProgress) : ViewModel() {

    private val intentSubject: PublishSubject<MainIntent> = PublishSubject.create()
    private val stateObservable: Observable<MainState> = compose()

    private fun compose(): Observable<MainState> {
        return intentSubject
            .map { // substitute compose
                when (it) {
                    is MainIntent.InitialIntent -> MainAction.LoadDataAction
                    is MainIntent.AdapterClickIntent -> MainAction.StartProgress(it.key)
                }
            }
            .cast(MainAction::class.java)
            .flatMap { // substitute compose
                when (it) {
                    is MainAction.LoadDataAction -> getData.execute()
                    is MainAction.StartProgress -> startProgress.execute(it.key).toObservable()
                }
            }
            .map { MainState(it) }
            .scan { oldState: MainState, newState: MainState ->
                newState
            }
            .replay(1)
            .autoConnect(0)
    }

    fun getState(): Observable<MainState> {
        return stateObservable
    }

    fun processIntent(intentsObservable: Observable<MainIntent>) {
        intentsObservable.subscribe(intentSubject)
    }
}
