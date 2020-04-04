package screens

import appium.core.components.Element
import appium.core.driver.AndroidDriverProvider
import appium.core.utilities.ElementLocator
import appium.core.utilities.Id

class LaunchScreen(private val androidDriverProvider: AndroidDriverProvider) {

    private val signInButtonLocator = ElementLocator.get(Id, "yahoo_sign_in_link")
    private val progressBarScreen = ProgressBarScreen(androidDriverProvider)

    fun clickSignInWithYahoo(): LoginScreen {
        val signInButton = Element(androidDriverProvider, signInButtonLocator)
        signInButton.click()
        progressBarScreen.waitForLoading()
        return LoginScreen(androidDriverProvider)
    }

}