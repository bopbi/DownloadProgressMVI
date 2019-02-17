package com.bobbyprabowo.android.myapplication

sealed class MainIntent {

    object InitialIntent: MainIntent()

    data class AdapterClickIntent(val key: String): MainIntent()
}
