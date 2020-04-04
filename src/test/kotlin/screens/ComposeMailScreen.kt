package screens

import appium.core.components.Element
import appium.core.components.WebViewTextBox
import appium.core.driver.AndroidDriverProvider
import appium.core.utilities.ElementLocator
import appium.core.utilities.Id

class ComposeMailScreen(private val androidDriverProvider: AndroidDriverProvider) {

    private val recipientEmailAddressSelector = "ymail_toInput"
    private val subjectSelector = "ymail_subjectField"
    private val contentSelector = "ymail_composeContent"
    private val addAttachmentButtonLocator = ElementLocator.get(Id, "addAttachmentButton")
    private val fileAttachmentLocator = ElementLocator.get(Id, "bottom_menu_file")
    private val sendButtonLocator = ElementLocator.get(Id, "send")

    fun addRecipient(recipientEmailAddress: String): ComposeMailScreen {
        val recipientTextBox = WebViewTextBox(androidDriverProvider, recipientEmailAddressSelector)
        recipientTextBox.setText(recipientEmailAddress)
        return this
    }

    fun addSubject(subject: String): ComposeMailScreen {
        val subjectTextBox = WebViewTextBox(androidDriverProvider, subjectSelector)
        subjectTextBox.setText(subject)
        return this
    }

    fun addContent(content: String): ComposeMailScreen {
        val contentTextBox = WebViewTextBox(androidDriverProvider, contentSelector)
        contentTextBox.setText(content)
        return this
    }

    fun clickAddAttachment(): FileManagerScreen {
        val attachmentButton = Element(androidDriverProvider, addAttachmentButtonLocator)
        attachmentButton.click()

        val fileAttachmentButton = Element(androidDriverProvider, fileAttachmentLocator)
        fileAttachmentButton.click()

        return FileManagerScreen(androidDriverProvider)
    }

    fun sendMail(): HomeScreen {
        val sendButton = Element(androidDriverProvider, sendButtonLocator)
        sendButton.click()
        return HomeScreen(androidDriverProvider)
    }
    
}