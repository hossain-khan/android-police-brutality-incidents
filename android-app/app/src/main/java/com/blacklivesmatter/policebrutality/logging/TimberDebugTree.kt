package com.blacklivesmatter.policebrutality.logging

import timber.log.Timber
import kotlin.math.abs

/**
 * DEBUG Logging tree for timber.
 * See https://github.com/JakeWharton/timber/blob/master/timber-sample/src/main/java/com/example/timber/ExampleApp.java
 */
class TimberDebugTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val newMessage = "[${getThreadName()}] $message"
        super.log(priority, tag, newMessage, t)
    }

    /**
     * Returns name of current thread, with some well known thread names replaced to make easier to understand.
     */
    private fun getThreadName(): String {
        var name = Thread.currentThread().name
        if (name.length > 10) {
            name = name.substring(0, 10) + "." + abs(name.hashCode() % 100)
        }
        return name
    }
}
