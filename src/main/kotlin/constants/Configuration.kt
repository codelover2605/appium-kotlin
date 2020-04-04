package constants

import java.nio.file.Paths

object Configuration {

    const val AppPackage = "com.yahoo.mobile.client.android.mail"

    val AppConfigurationFilePath =
        Paths.get(PathConstants.RootDirectory, "src/main/resources/AppConfiguration.json").toString()

    val ApkPath = Paths.get(PathConstants.RootDirectory, "app/yahoo-mail.apk").toString()

}