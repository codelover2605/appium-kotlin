package filehandlers

import constants.PathConstants
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Paths

object FileWriter {

    fun writeToFile(fileContents: ByteArray, destinationFileName: String) {
        val fileDownloadDirectory = File(PathConstants.RootDirectory, "files")
        if (!fileDownloadDirectory.exists()) {
            fileDownloadDirectory.mkdir()
        }

        val fileStream = FileOutputStream(Paths.get(fileDownloadDirectory.absolutePath, destinationFileName).toString())
        fileStream.write(fileContents)
        fileStream.close()
    }

}