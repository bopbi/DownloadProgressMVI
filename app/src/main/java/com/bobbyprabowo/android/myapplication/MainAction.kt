package com.bobbyprabowo.android.myapplication

sealed class MainAction {

    object LoadDataAction: MainAction()

    data class StartProgress(val key: String): MainAction()
}
