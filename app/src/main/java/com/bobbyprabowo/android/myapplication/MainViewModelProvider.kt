package com.bobbyprabowo.android.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelProvider: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val dataRepository = DataRepository()
        val getData = GetData(dataRepository)
        val startProgress = StartProgress(dataRepository)
        return MainViewModel(getData, startProgress) as T
    }

}
