package tests

import appium.core.utilities.RandomGenerator
import constants.PathConstants
import entities.EmailCredential
import filehandlers.JsonParser
import org.testng.annotations.Test
import screens.HomeScreen
import screens.LaunchScreen
import screens.OnboardScreen
import java.nio.file.Paths

class EmailValidationTests : TestBase() {

    @Test(description = "Validation Email Functionality")
    fun validateEmailFunctionality() {
        val emailCredentialsPath = Paths.get(PathConstants.TestResourcesDirectory, "EmailCredentials.json").toString()
        val attachmentFilePath = Paths.get(PathConstants.TestResourcesDirectory, "Attachment.pdf").toString()
        val devicePathToPickAttachment = "AppiumFiles/Attachment.pdf"

        val emailCredentials = JsonParser.getData<Array<EmailCredential>>(emailCredentialsPath)
        val yahooAccount = "yahoo"
        val senderEmailCredentials = emailCredentials.first()
        val recipientEmailCredentials = emailCredentials.last()
        val emailSubjectAndContent = RandomGenerator.generateUUID()

        deviceDriver.pushFile(attachmentFilePath, devicePathToPickAttachment)

        /***
         * Setup Sender and recipient email accounts
         */
        deviceDriver
            .launchApp<LaunchScreen>()
            .clickSignInWithYahoo()
            .addEmailAccount<OnboardScreen>(emailCredential = senderEmailCredentials)
            .clickNextButton()
            .initiateAddingAnotherAccount(account = yahooAccount)
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
            .attachFile(devicePathToPickAttachment)
            .sendMail()
            .openSentFolder()
            .waitTillMailPresent(emailSubjectAndContent)

        HomeScreen(driver)
            .switchAccount(recipientEmailCredentials.email)
            .waitTillMailPresent(emailSubjectAndContent)
            .openMail(emailSubjectAndContent)
            .downloadAttachment()

        deviceDriver.pullFile("Attachment.pdf")
    }

}