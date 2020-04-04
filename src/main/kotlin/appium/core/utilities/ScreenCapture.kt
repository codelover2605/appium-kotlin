package appium.core.utilities

import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import java.io.File

object ScreenCapture {

    fun takeScreenshot(driver: WebDriver): File {
        try {
            val takeScreenshot = driver as TakesScreenshot
            return takeScreenshot.getScreenshotAs(OutputType.FILE)
        } catch (error: Exception) {
            throw error("Error taking screenshot. ${error.message}")
        }
    }
}