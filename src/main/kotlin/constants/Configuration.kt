package constants

import java.nio.file.Paths

object Configuration {

    const val AppPackage = "com.yahoo.mobile.client.android.mail"
    val AppConfigurationFilePath =
        Paths.get(System.getProperty("user.dir", "src/main/resources/AppConfiguration.json")).toString()

    val ApkPath = Paths.get(System.getProperty("user.dir"), "app/yahoo-mail.apk").toString()

}