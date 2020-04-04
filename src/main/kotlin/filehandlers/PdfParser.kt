package filehandlers

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

object PdfParser {

    fun getPdfContent(
        filePath: String, startPage: Int = 1, endPage: Int? = null
    ): String {
        val textStripper = PDFTextStripper()
        textStripper.startPage = startPage

        if (endPage != null) {
            textStripper.endPage = endPage
        }

        if (!File(filePath).exists()) {
            throw error("File $filePath not found")
        }

        val document = PDDocument.load(File(filePath))
        return if (!document.isEncrypted) {
            textStripper.getText(document)
        } else {
            throw error("Document is encrypted")
        }
    }

}