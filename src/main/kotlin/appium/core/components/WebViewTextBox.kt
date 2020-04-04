package appium.core.components

import appium.core.components.contracts.Settable
import appium.core.driver.AndroidDriverProvider
import appium.core.utilities.ElementLocator
import appium.core.utilities.Xpath
import org.openqa.selenium.Keys
import org.openqa.selenium.interactions.Actions

class WebViewTextBox(
    private val androidDriverProvider: AndroidDriverProvider,
    idAttribute: String
) : Settable {

    private val locator = ElementLocator.get(
        Xpath, "//android.widget.EditText[contains(@resource-id, '$idAttribute')]"
    )

    override fun setText(textValue: CharSequence?) {
        try {
            val element = Element(androidDriverProvider, locator)
            element.waitTillVisible()
            element.click()
            element.waitTillVisible()

            Actions(androidDriverProvider.driverInstance)
                .sendKeys(textValue)
                .sendKeys(Keys.ENTER)
                .perform()
        } catch (error: Exception) {
            throw error(error)
        }
    }

}