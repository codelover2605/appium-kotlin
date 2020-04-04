package constants

import java.nio.file.Paths

object PathConstants {

    val RootDirectory = Paths.get(System.getProperty("user.dir")).toString()
    val SrcResourcesDirectory = Paths.get(RootDirectory, "src/main/resources").toString()
    val TestResourcesDirectory = Paths.get(RootDirectory, "src/test/resources").toString()

}