package com.bobbyprabowo.android.myapplication

import io.reactivex.Completable

class StartProgress(private val dataRepository: DataRepository) {

    fun execute(key: String): Completable = dataRepository.startDownload(key)
}
