package tests

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
        val emailCredentials = JsonParser.getData<Array<EmailCredential>>(emailCredentialsPath)
        val yahooAccount = "yahoo"
        val senderEmailCredentials = emailCredentials.first()
        val recipientEmailCredentials = emailCredentials.last()

        deviceDriver
            .launchApp<LaunchScreen>()
            .clickSignInWithYahoo()
            .addEmailAccount<OnboardScreen>(emailCredential = senderEmailCredentials)
            .clickNextButton()
            .initiateAddingAnotherAccount(account = yahooAccount)
            .addEmailAccount<HomeScreen>(emailCredential = recipientEmailCredentials)
            .skipTutorial()
    }

}