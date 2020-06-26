package com.blacklivesmatter.policebrutality.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.After
import org.junit.Rule
import org.mockito.Mockito
import timber.log.Timber

open class BaseTest {
    @get:Rule
    var liveDataThreadRule = InstantTaskExecutorRule()

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

    /**
     * See [Memory leak in mockito-inline...](https://github.com/mockito/mockito/issues/1614)
     */
    @After
    fun clearMocks() {
        Mockito.framework().clearInlineMocks()
    }
}
