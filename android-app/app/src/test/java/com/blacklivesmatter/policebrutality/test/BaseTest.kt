package com.blacklivesmatter.policebrutality.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import timber.log.Timber

@ExperimentalCoroutinesApi
open class BaseTest {
    @get:Rule
    var liveDataThreadRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    init {
        // Do this setup as part of init rather than @Before to allow for usages from init in sub classes.
        setupLogging()
    }

    private fun setupLogging() {
        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                System.out.println("${tag ?: ""} $message ${t ?: ""}")
            }
        })
    }

    @Before
    fun setupBase() {
        // https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/
        Dispatchers.setMain(testDispatcher)
    }

    /**
     * See [Memory leak in mockito-inline...](https://github.com/mockito/mockito/issues/1614)
     */
    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()

        // https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}
