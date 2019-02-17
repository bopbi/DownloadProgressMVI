package com.bobbyprabowo.android.myapplication

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers

class DataRepository(private val observerScheduler: Scheduler = Schedulers.io()) {

    private val upstream: BehaviorProcessor<Map<String, Data>> = BehaviorProcessor.create()

    init {
        val initialDataList = mutableMapOf<String, Data>()
        for (number in 1..20) {
            val key = "item $number"
            val dataToInserted = Data(key, onProgress = false, progress = 0, finished = false)
            initialDataList[key] = dataToInserted
        }
        upstream.onNext(initialDataList)
    }

    fun startDownload(itemId: String): Completable {
        return Observable
            .create<Data> {
                for (progress in 1..100) {
                    if (progress != 100) {
                        it.onNext(Data(itemId, onProgress = true, progress = progress, finished = false))
                    } else {
                        it.onNext(Data(itemId, onProgress = false, progress = progress, finished = true))
                    }
                }
                it.onComplete()
            }
            .doOnNext {
                if (upstream.hasValue()) {
                    val currentDownloadState = upstream.value
                    val newDownloadState = HashMap(currentDownloadState)
                    newDownloadState[itemId] = it
                    upstream.onNext(newDownloadState)
                }
            }.subscribeOn(observerScheduler).ignoreElements()
    }

    fun getDownloadState(): Observable<Map<String, Data>> {
        return upstream.toObservable()
    }
}
