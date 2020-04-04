package tests

import appium.core.driver.AndroidDriverProvider
import appium.core.driver.DeviceDriver
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Parameters

open class TestBase {

    protected lateinit var driver: AndroidDriverProvider
    protected lateinit var deviceDriver: DeviceDriver

    @Parameters("deviceId")
    @BeforeMethod
    fun initialize(deviceId: String) {
        driver = AndroidDriverProvider(deviceId)
        deviceDriver = DeviceDriver(driver)
        deviceDriver.uninstallApp()
        deviceDriver.installApp()
    }

    @AfterMethod
    fun tearDown() {
        deviceDriver.closeApp()
        driver.disposeDriver()
    }

}