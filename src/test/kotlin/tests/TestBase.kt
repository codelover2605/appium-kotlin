package tests

import appium.core.driver.AndroidDriverProvider
import appium.core.driver.DeviceDriver
import appium.core.utilities.DeviceFinder
import constants.PathConstants
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import java.nio.file.Paths

open class TestBase {

    protected lateinit var driver: AndroidDriverProvider
    protected lateinit var deviceDriver: DeviceDriver

    protected val attachmentFileName = "Attachment.pdf"
    protected val attachmentFilePath = Paths.get(PathConstants.TestResourcesDirectory, attachmentFileName).toString()
    protected val deviceFilePathToPickAttachment = "AppiumFiles/$attachmentFileName"

    @BeforeMethod
    fun initialize() {
        val deviceId = DeviceFinder.getConnectedDevices().first()
        driver = AndroidDriverProvider(deviceId)
        deviceDriver = DeviceDriver(driver)
        deviceDriver.uninstallApp()
        deviceDriver.installApp()
        deviceDriver.pushFile(attachmentFilePath, deviceFilePathToPickAttachment)
    }

    @AfterMethod
    fun tearDown() {
        deviceDriver.closeApp()
        driver.disposeDriver()
    }

}