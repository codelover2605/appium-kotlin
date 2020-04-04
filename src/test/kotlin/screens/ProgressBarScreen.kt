package screens

import appium.core.components.Element
import appium.core.driver.AndroidDriverProvider
import appium.core.utilities.ElementLocator
import appium.core.utilities.Id

class ProgressBarScreen(private val androidDriverProvider: AndroidDriverProvider) {

    private val progressBarLocator = ElementLocator.get(Id, "progressBar")

    fun waitForLoading() {
        val progressBar = Element(androidDriverProvider, progressBarLocator)
        if(!progressBar.waitTillInVisible(30)) {
            throw error("Progress bar not dismissed after 20 seconds")
        }
    }

}