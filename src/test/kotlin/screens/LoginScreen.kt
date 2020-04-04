package screens

import appium.core.components.WebViewTextBox
import appium.core.driver.AndroidDriverProvider
import entities.EmailCredential

class LoginScreen(val androidDriverProvider: AndroidDriverProvider) {

    val userNameTextBoxSelector = "login-username"
    val passwordTextBoxLocator = "login-passwd"
    val progressBarScreen = ProgressBarScreen(androidDriverProvider)

    inline fun <reified T> addEmailAccount(emailCredential: EmailCredential): T {
        val usernameTextBox = WebViewTextBox(androidDriverProvider, userNameTextBoxSelector)
        usernameTextBox.setText(emailCredential.username)
        progressBarScreen.waitForLoading()

        val passwordTextBox = WebViewTextBox(androidDriverProvider, passwordTextBoxLocator)
        passwordTextBox.setText(emailCredential.password)
        progressBarScreen.waitForLoading()

        val constructor = T::class.java.getConstructor(AndroidDriverProvider::class.java)
        return constructor.newInstance(androidDriverProvider)
    }

}