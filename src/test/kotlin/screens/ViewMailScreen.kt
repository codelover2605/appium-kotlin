package screens

import appium.core.components.Element
import appium.core.driver.AndroidDriverProvider
import appium.core.utilities.ContentDesc
import appium.core.utilities.ElementLocator
import appium.core.utilities.Id
import appium.core.utilities.Text

class ViewMailScreen(private val androidDriverProvider: AndroidDriverProvider) {

    private val attachmentLocator = ElementLocator.get(Id, "attachment_title")
    private val threeDotsMenuLocator = ElementLocator.get(ContentDesc, "More options")
    private val downloadButtonLocator = ElementLocator.get(Text, "Download")

    fun downloadAttachment() {
        val attachment = Element(androidDriverProvider, attachmentLocator)
        attachment.click()

        val threeDotsMenu = Element(androidDriverProvider, threeDotsMenuLocator)
        threeDotsMenu.click()

        val downloadButton = Element(androidDriverProvider, downloadButtonLocator)
        downloadButton.click()
    }

}