package screens

import appium.core.components.Element
import appium.core.driver.AndroidDriverProvider
import appium.core.utilities.ElementLocator
import appium.core.utilities.Id

class HomeScreen(private val androidDriverProvider: AndroidDriverProvider) {

    private val tutorialCancelButtonLocator = ElementLocator.get(Id, "close_button")

    fun skipTutorial() {
        val skipTutorialButton = Element(androidDriverProvider, tutorialCancelButtonLocator)
        skipTutorialButton.waitTillVisible()
        skipTutorialButton.click()
    }

}