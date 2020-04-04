package screens

import appium.core.components.Element
import appium.core.driver.AndroidDriverProvider
import appium.core.utilities.ElementLocator
import appium.core.utilities.Id
import appium.core.utilities.Text

class OnboardScreen(private val androidDriverProvider: AndroidDriverProvider) {

    private val nextButtonLocator = ElementLocator.get(Id, "set_theme")
    private val progressBarScreen = ProgressBarScreen(androidDriverProvider)

    fun clickNextButton(): OnboardScreen {
        val nextButton = Element(androidDriverProvider, nextButtonLocator)
        nextButton.waitTillVisible()
        nextButton.click()
        progressBarScreen.waitForLoading()
        return this
    }

    fun initiateAddingAnotherAccount(account: String = "yahoo"): LoginScreen {
        val accountImage = Element(androidDriverProvider, ElementLocator.get(Text, account))
        accountImage.click()

        val addAccount = Element(androidDriverProvider, ElementLocator.get(Text, "Add Account"))
        addAccount.click()
        progressBarScreen.waitForLoading()

        return LoginScreen(androidDriverProvider)
    }

}