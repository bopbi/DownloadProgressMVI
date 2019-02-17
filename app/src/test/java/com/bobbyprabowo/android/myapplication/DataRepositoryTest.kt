package com.bobbyprabowo.android.myapplication

import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DataRepositoryTest {

    private lateinit var dataRepository: DataRepository

    @Before
    fun setup() {
        dataRepository = DataRepository(Schedulers.trampoline())
    }

    @Test
    fun start() {
        val stateTestObserver = dataRepository.getDownloadState().test()
        val number = 4
        val key = "item $number"

        val startTestObserver = dataRepository.startDownload(key).test()
        startTestObserver.assertComplete()

        val latestState = stateTestObserver.values().last()
        assertEquals(latestState[key]?.finished, true)
    }
}
