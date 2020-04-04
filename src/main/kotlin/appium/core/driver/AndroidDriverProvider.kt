package appium.core.driver

import constants.Configuration
import filehandlers.JsonParser
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.AndroidMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType
import io.appium.java_client.service.local.AppiumDriverLocalService
import io.appium.java_client.service.local.AppiumServiceBuilder
import io.appium.java_client.service.local.flags.GeneralServerFlag
import org.openqa.selenium.remote.DesiredCapabilities
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URL
import java.util.concurrent.TimeUnit

class AndroidDriverProvider(
    private val udid: String,
    private var port: Int? = null,
    private var systemPort: String? = null,
    private val autoGrantPermissions: Boolean = true
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private var driver: AndroidDriver<MobileElement>? = null
    private var appiumService: AppiumDriverLocalService? = null

    init {
        port = port ?: 4723
        systemPort = systemPort ?: "8200"
    }

    private val localHostIpAddress = "127.0.0.1"

    internal val driverInstance: AndroidDriver<MobileElement> by lazy {
        if (driver == null) {
            logger.info("Initializing Driver...")
            driver = initializeDriver()
            driver?.manage()?.timeouts()?.implicitlyWait(5, TimeUnit.SECONDS)
        }

        return@lazy driver ?: error("Error Initializing driver")
    }

    fun disposeDriver() {
        try {
            driver?.quit()
            stopAppiumServer()
        } catch (error: Exception) {
            throw error(error)
        }
    }

    private fun initializeDriver(): AndroidDriver<MobileElement> {
        return try {
            startAppiumServer()
            val capabilities = getCapabilities()
            AndroidDriver<MobileElement>(URL("http://${localHostIpAddress}:$port/wd/hub"), capabilities)
        } catch (error: Exception) {
            logger.error("Error initializing Driver: ${error.message}")
            throw error(error)
        }
    }

    private fun getCapabilities(): DesiredCapabilities {
        return try {
            val appConfigurationFile = File(Configuration.AppConfigurationFilePath)

            if (!appConfigurationFile.exists()) {
                throw error("${Configuration.AppConfigurationFilePath} doesn't exist")
            }

            val configuration: Map<String, *> = JsonParser.getData(Configuration.AppConfigurationFilePath)

            val capabilities = DesiredCapabilities()
            configuration.forEach { (key, value) -> capabilities.setCapability(key, value) }

            capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, autoGrantPermissions)
            capabilities.setCapability(MobileCapabilityType.APP, Configuration.ApkPath)
            capabilities.setCapability(MobileCapabilityType.UDID, udid)

            logger.info("Device Capabilities: ${capabilities.toJson()}")
            capabilities
        } catch (error: Exception) {
            logger.error("Error getting desired capabilities. ${error.message}")
            throw error(error)
        }
    }

    private fun startAppiumServer() {
        try {
            val capabilities = DesiredCapabilities()
            capabilities.setCapability(MobileCapabilityType.UDID, udid)
            capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort)

            appiumService = AppiumDriverLocalService.buildService(
                AppiumServiceBuilder()
                    .withIPAddress(localHostIpAddress)
                    .usingPort(port ?: error("No Port specified to start Appium Server"))
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                    .withCapabilities(capabilities)
            )

            appiumService?.start()
        } catch (error: Exception) {
            logger.error("Error starting Appium Server. ${error.message}")
            throw error(error)
        }
    }

    private fun stopAppiumServer() {
        try {
            appiumService?.stop()
        } catch (error: Exception) {
            logger.error("Error stopping Appium Server. ${error.message}")
            throw error(error)
        }
    }

}