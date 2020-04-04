package screens

import appium.core.components.Element
import appium.core.driver.AndroidDriverProvider
import appium.core.utilities.ElementLocator
import appium.core.utilities.Id

class HomeScreen(private val androidDriverProvider: AndroidDriverProvider) {

    private val tutorialCancelButtonLocator = ElementLocator.get(Id, "close_button")
    private val composeButtonLocator = ElementLocator.get(Id, "right_button_1")

    fun skipTutorial(): HomeScreen {
        val skipTutorialButton = Element(androidDriverProvider, tutorialCancelButtonLocator)
        skipTutorialButton.waitTillVisible()
        skipTutorialButton.click()
        return this
    }

    fun clickCompose(): ComposeMailScreen {
        val composeButton = Element(androidDriverProvider, composeButtonLocator)
        composeButton.click()
        return ComposeMailScreen(androidDriverProvider)
    }

}