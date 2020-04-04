package appium.core.utilities

import java.util.*

object RandomGenerator {

    fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }

}