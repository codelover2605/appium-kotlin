package filehandlers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File

object JsonParser {

    inline fun <reified T> getData(jsonFilePath: String): T {
        try {
            val bufferedReader: BufferedReader = File(jsonFilePath).bufferedReader()
            val jsonData = bufferedReader.use { it.readText() }
            return Gson().fromJson(jsonData, object : TypeToken<T>() {}.type)
        } catch (error: Exception) {
            throw error(error)
        }
    }

}