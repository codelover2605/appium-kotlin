package appium.core.components

import appium.core.components.contracts.Clickable
import appium.core.components.contracts.Gettable
import appium.core.components.contracts.Touchable
import appium.core.driver.AndroidDriverProvider
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.AndroidElement
import io.appium.java_client.android.AndroidTouchAction
import io.appium.java_client.touch.offset.ElementOption
import org.openqa.selenium.*
import org.openqa.selenium.support.pagefactory.ByChained
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import org.slf4j.LoggerFactory
import java.time.Duration

open class Element(
    androidDriverProvider: AndroidDriverProvider,
    private var locator: By,
    private var parentLocator: By? = null
) : Gettable, Touchable, Clickable {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private var driverInstance: AndroidDriver<MobileElement> = androidDriverProvider.driverInstance
    private var touchAction: AndroidTouchAction = AndroidTouchAction(driverInstance)

    override fun getAttributeValue(attributeName: String): String {
        try {
            val wait = FluentWait(driverInstance)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(ElementNotInteractableException::class.java)
                .ignoring(StaleElementReferenceException::class.java)
                .ignoring(NoSuchElementException::class.java)
                .ignoring(InvalidElementStateException::class.java)

            wait.until { hasAttribute(attributeName) }
            return getElement().getAttribute(attributeName)
        } catch (error: Exception) {
            logger.error("Error Getting Attribute Value. ${error.message}")
            throw error(error)
        }
    }

    override fun getTextValue(): String {
        try {
            waitTillVisible()
            return getElement().text
        } catch (error: Exception) {
            logger.error("Error getting text value. ${error.message}")
            throw error(error)
        }
    }

    override fun click() {
        try {
            waitTillVisible()
            getElement().click()
        } catch (error: Exception) {
            logger.error("Error clicking Element $locator. ${error.message}")
            throw error(error)
        }
    }

    override fun tap() {
        try {
            touchAction.tap(ElementOption.element(getElement())).perform()
        } catch (error: Exception) {
            logger.error("Error Tapping Element. ${error.message}")
            throw error(error)
        }
    }

    override fun longPress() {
        try {
            touchAction.longPress(ElementOption.element(getElement())).perform()
        } catch (error: Exception) {
            logger.error("Error performing Long Pressing. ${error.message}")
            throw Exception(error)
        }
    }

    fun waitTillVisible(timeOutInSeconds: Long = 10) {
        try {
            val wait = FluentWait(driverInstance)
                .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(ElementNotInteractableException::class.java)
                .ignoring(InvalidElementStateException::class.java)
                .ignoring(NoSuchElementException::class.java)
                .ignoring(StaleElementReferenceException::class.java)

            wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
        } catch (error: Exception) {
            logger.error("Error waiting for element to be visible. ${error.message}")
            throw error(error)
        }
    }

    fun waitTillInVisible(timeOutInSeconds: Long = 10): Boolean {
        return try {
            val wait = FluentWait(driverInstance)
                .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(ElementNotInteractableException::class.java)
                .ignoring(NoSuchElementException::class.java)

            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator))
        } catch (error: Exception) {
            logger.error("Error waiting for element to be invisible. ${error.message}")
            throw error(error)
        }
    }

    fun waitTillPresent(timeOutInSeconds: Long = 10): Boolean {
        return try {
            val wait = FluentWait(driverInstance)
                .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(NoSuchElementException::class.java)

            val element = wait.until(ExpectedConditions.presenceOfElementLocated(locator))
            return element != null
        } catch (error: Exception) {
            logger.error("Error waiting for element to be present. ${error.message}")
            false
        }
    }

    private fun hasAttribute(attributeName: String): Boolean {
        val attributeExists = getElement().getAttribute(attributeName)
        return attributeExists != null
    }

    private fun getElement(): AndroidElement {
        return try {
            val wait = FluentWait(driverInstance)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(NoSuchElementException::class.java)

            wait.until(ExpectedConditions.presenceOfElementLocated(buildLocator())) as AndroidElement
        } catch (error: Exception) {
            throw error(error)
        }
    }

    private fun buildLocator(): By {
        return if (parentLocator != null)
            ByChained(parentLocator, locator)
        else locator
    }

}