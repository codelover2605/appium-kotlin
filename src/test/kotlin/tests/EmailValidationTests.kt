package tests

import appium.core.utilities.RandomGenerator
import constants.PathConstants
import entities.EmailCredential
import filehandlers.JsonParser
import filehandlers.PdfParser
import org.testng.annotations.Test
import screens.HomeScreen
import screens.LaunchScreen
import screens.OnboardScreen
import java.nio.file.Paths

class EmailValidationTests : TestBase() {

    @Test(description = "Validation Email Functionality")
    fun validateEmailFunctionality() {
        val emailCredentialsPath = Paths.get(PathConstants.TestResourcesDirectory, "EmailCredentials.json").toString()
        val emailCredentials = JsonParser.getData<Array<EmailCredential>>(emailCredentialsPath)
        val senderEmailCredentials = emailCredentials.first()
        val recipientEmailCredentials = emailCredentials.last()
        val emailSubjectAndContent = RandomGenerator.generateUUID()

        /***
         * Setup Sender and recipient email accounts
         */
        deviceDriver
            .launchApp<LaunchScreen>()
            .clickSignInWithYahoo()
            .addEmailAccount<OnboardScreen>(emailCredential = senderEmailCredentials)
            .clickNextButton()
            .initiateAddingAnotherAccount()
            .addEmailAccount<HomeScreen>(emailCredential = recipientEmailCredentials)
            .skipTutorial()

        /**
         * Send Email with attachment to Recipient
         */
        HomeScreen(driver)
            .switchAccount(senderEmailCredentials.email)
            .clickCompose()
            .addRecipient(recipientEmailCredentials.email)
            .addSubject(emailSubjectAndContent)
            .addContent(emailSubjectAndContent)
            .clickAddAttachment()
            .attachFile(deviceFilePathToPickAttachment)
            .sendMail()
            .openSentFolder()
            .waitTillMailPresent(emailSubjectAndContent)

        /**
         * Switch to Recipient Account and Download Attachment
         */
        HomeScreen(driver)
            .switchAccount(recipientEmailCredentials.email)
            .waitTillMailPresent(emailSubjectAndContent)
            .openMail(emailSubjectAndContent)
            .downloadAttachment()

        /**
         * Validate Attachment Content
         */
        val downloadedAttachmentFilePath = deviceDriver.pullFile(attachmentFileName)

        val sentAttachmentFileContent = PdfParser.getPdfContent(attachmentFilePath)
        val receivedAttachmentFileContent = PdfParser.getPdfContent(downloadedAttachmentFilePath)

        assert(sentAttachmentFileContent == receivedAttachmentFileContent)
        { "Sent: $sentAttachmentFileContent but received $receivedAttachmentFileContent" }

    }

}