package appium.core.utilities

import java.io.BufferedReader
import java.io.InputStreamReader

object DeviceFinder {

    fun getConnectedDevices(): MutableList<String> {
        return try {
            val getDevices = "adb devices"
            val devices = mutableListOf<String>()
            val process = Runtime.getRuntime().exec(getDevices)
            process.waitFor()
            val inputStream = BufferedReader(InputStreamReader(process.inputStream))

            val lines = inputStream.readLines()
            if (lines.isNotEmpty()) {
                (1 until lines.size)
                    .asSequence()
                    .filter { lines[it].isNotEmpty() }
                    .mapTo(devices) { lines[it].split("\\t".toRegex()).first().trim() }
            } else {
                throw error("No Connected devices found")
            }
            devices
        } catch (error: Exception) {
            throw error(error)
        }
    }
}