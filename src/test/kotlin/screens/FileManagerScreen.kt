package screens

import appium.core.components.Element
import appium.core.driver.AndroidDriverProvider
import appium.core.utilities.ElementLocator
import appium.core.utilities.Id
import appium.core.utilities.Text

class FileManagerScreen(private val androidDriverProvider: AndroidDriverProvider) {

    private val attachButtonLocator = ElementLocator.get(Id, "attachment_attach_button")

    fun attachFile(path: String): ComposeMailScreen {
        path.split("/").forEach { directory ->
            val file = Element(androidDriverProvider, ElementLocator.get(Text, directory))
            file.waitTillVisible()
            file.click()
        }

        val attachButton = Element(androidDriverProvider, attachButtonLocator)
        attachButton.click()
        return ComposeMailScreen(androidDriverProvider)
    }

}