package screens

import appium.core.components.Element
import appium.core.driver.AndroidDriverProvider
import appium.core.utilities.ElementLocator
import appium.core.utilities.Id
import appium.core.utilities.Text

class HomeScreen(private val androidDriverProvider: AndroidDriverProvider) {

    private val tutorialCancelButtonLocator = ElementLocator.get(Id, "close_button")
    private val composeButtonLocator = ElementLocator.get(Id, "right_button_1")
    private val folderSwitcherIconLocator = ElementLocator.get(Id, "item_primary_icon")
    private val sentFolderLocator = ElementLocator.get(Text, "Sent")
    private val avatarButtonLocator = ElementLocator.get(Id, "avatar_button")

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

    fun openSentFolder(): HomeScreen {
        val folderSwitcherIcon = Element(androidDriverProvider, folderSwitcherIconLocator)
        folderSwitcherIcon.click()

        val sentFolder = Element(androidDriverProvider, sentFolderLocator)
        sentFolder.click()

        return this
    }

    fun waitTillMailPresent(subject: String): HomeScreen {
        val expectedMail = Element(androidDriverProvider, ElementLocator.get(Text, subject))
        if (!expectedMail.waitTillPresent()) {
            throw error("Mail with Subject $subject not Sent")
        }

        return this
    }

    fun switchAccount(accountEmail: String): HomeScreen {
        val avatarButton = Element(androidDriverProvider, avatarButtonLocator)
        avatarButton.click()

        val accountToBeSwitched = Element(androidDriverProvider, ElementLocator.get(Text, accountEmail))
        accountToBeSwitched.click()

        return this
    }

    fun openMail(subject: String): ViewMailScreen {
        val email = Element(androidDriverProvider, ElementLocator.get(Text, subject))
        email.click()

        return ViewMailScreen(androidDriverProvider)
    }

}