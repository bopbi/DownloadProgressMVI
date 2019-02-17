package com.bobbyprabowo.android.myapplication

class GetData(private val dataRepository: DataRepository) {

    fun execute() = dataRepository.getDownloadState()
}
