package constants

import java.nio.file.Paths

object PathConstants {

    val RootDirectory = Paths.get(System.getProperty("user.dir")).toString()
    val ResourcesDirectory = Paths.get(RootDirectory, "src/main/resources").toString()

}