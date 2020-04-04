package appium.core.driver

import constants.Configuration
import constants.PathConstants
import io.appium.java_client.android.AndroidTouchAction
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Paths
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
            throw error("Error launching app")
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

    fun pressBackButton() {
        try {
            instance.navigate().back()
        } catch (error: Exception) {
            logger.error("Error pressing back button. ${error.message}")
            throw error("Error pressing back button. ${error.message}")
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

    fun swipeRight() {
        try {
            val size = instance.manage().window().size
            val anchor = (size.height * 0.5).toInt()
            val startPoint = (size.width * 0.01).toInt()
            val endPoint = (size.width * 0.9).toInt()
            swipeHorizontal(startPoint, endPoint, anchor)
        } catch (error: Exception) {
            logger.error("Error swiping right. ${error.message}")
            throw error("Error swiping right. ${error.message}")
        }
    }

    fun swipeLeft() {
        try {
            val size = instance.manage().window().size
            val anchor = (size.height * 0.5).toInt()
            val startPoint = (size.width * 0.9).toInt()
            val endPoint = (size.width * 0.01).toInt()
            swipeHorizontal(startPoint, endPoint, anchor)
        } catch (error: Exception) {
            logger.error("Error swiping left. ${error.message}")
            throw error("Error swiping left. ${error.message}")
        }
    }

    fun swipeDown() {
        try {
            val size = instance.manage().window().size
            val anchor = (size.width * 0.5).toInt()
            val startPoint = (size.height * 0.2).toInt()
            val endPoint = (size.height * 0.8).toInt()
            swipeVertical(startPoint, endPoint, anchor)
        } catch (error: Exception) {
            logger.error("Error swiping down. ${error.message}")
            throw error("Error swiping down. ${error.message}")
        }
    }

    fun swipeUp() {
        try {
            val size = instance.manage().window().size
            val anchor = (size.width * 0.5).toInt()
            val startPoint = (size.height * 0.8).toInt()
            val endPoint = (size.height * 0.2).toInt()

            swipeVertical(startPoint, endPoint, anchor)
        } catch (error: Exception) {
            logger.error("Error swiping up. ${error.message}")
            throw error("Error swiping Up. ${error.message}")
        }
    }

    fun pushFile(fileToBePushed: String) {
        try {
            val srcFilePath = Paths.get(PathConstants.ResourcesDirectory, fileToBePushed).toString()
            val devicePath = "/sdcard/$fileToBePushed"
            instance.pushFile(devicePath, File(srcFilePath))
        } catch (error: Exception) {
            logger.error("Error pushing file. ${error.message}")
            throw error("Error pushing file. ${error.message}")
        }
    }

    private fun swipeHorizontal(startPoint: Int, endPoint: Int, anchor: Int) {
        try {
            val touchAction = AndroidTouchAction(instance)
            touchAction.press(PointOption.point(startPoint, anchor))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(endPoint, anchor))
                .release()
                .perform()
        } catch (error: Exception) {
            throw Exception("Error Swiping Horizontal")
        }
    }

    private fun swipeVertical(startPoint: Int, endPoint: Int, anchor: Int) {
        try {
            val touchAction = AndroidTouchAction(instance)
            touchAction.press(PointOption.point(anchor, startPoint))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(anchor, endPoint))
                .release()
                .perform()
        } catch (error: Exception) {
            throw Exception("Error swiping Vertical")
        }
    }
}