package appium.core.driver

import constants.Configuration
import filehandlers.FileWriter
import org.awaitility.Awaitility
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.time.Duration

class DeviceDriver(val androidDriverProvider: AndroidDriverProvider) {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)
    val instance = androidDriverProvider.driverInstance

    inline fun <reified T> launchApp(): T {
        try {
            instance.launchApp()
            val constructor = T::class.java.getConstructor(AndroidDriverProvider::class.java)
            return constructor.newInstance(androidDriverProvider)
        } catch (error: Exception) {
            logger.error("Error Launching App. ${error.message}")
            throw error(error)
        }
    }

    fun closeApp() {
        try {
            instance.closeApp()
        } catch (error: Exception) {
            logger.error("Error closing App. ${error.message}")
            throw error("Error closing app")
        }
    }

    fun installApp() {
        try {
            if (!instance.isAppInstalled(Configuration.AppPackage)) {
                instance.installApp(Configuration.ApkPath)
            } else {
                logger.info("App already installed")
            }

        } catch (error: Exception) {
            logger.error("Error installing Application. ${error.message}")
            throw error("Error installing Application. ${error.message}")
        }
    }

    fun uninstallApp(): Boolean {
        return try {
            instance.removeApp(Configuration.AppPackage)
            !instance.isAppInstalled(Configuration.AppPackage)
        } catch (error: Exception) {
            logger.error("Error uninstalling Application. ${error.message}")
            throw error("Error installing Application. ${error.message}")
        }
    }

    fun pushFile(sourceFilePath: String, destinationFilePath: String) {
        try {
            val fileToBePushedToDevice = File(sourceFilePath)
            instance.pushFile("/sdcard/$destinationFilePath", fileToBePushedToDevice)
        } catch (error: Exception) {
            logger.error("Error pushing file. ${error.message}")
            throw error(error)
        }
    }

    fun pullFile(fileName: String, timeOutInSeconds: Long = 30) {
        var fileContents: ByteArray? = null
        Awaitility.await().ignoreExceptions().atMost(Duration.ofSeconds(timeOutInSeconds)).until {
            fileContents = instance.pullFile("/sdcard/Download/$fileName")
            fileContents?.isNotEmpty() == true
        }

        if (fileContents == null) {
            throw error("File $fileName not found")
        }

        FileWriter.writeToFile(fileContents ?: error("File Content is null"), fileName)
    }

}