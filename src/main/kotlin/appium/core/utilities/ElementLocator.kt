package appium.core.utilities

import constants.Configuration
import org.openqa.selenium.By

const val Id = "id"
const val Xpath = "xpath"
const val Text = "text"
const val PartialText = "partialText"
const val ResourceId = "resourceId"
const val ContentDesc = "contentDesc"

object ElementLocator {

    fun get(locatorType: String, locatorValue: String): By {
        return when (locatorType) {
            Xpath -> By.xpath(locatorValue)
            Text -> By.xpath("//*[${buildCaseInsensitiveXpath(locatorValue, false)}]")
            PartialText -> By.xpath("//*[${buildCaseInsensitiveXpath(locatorValue)}]")
            ResourceId, Id -> By.xpath("//*[@resource-id='${Configuration.AppPackage}:id/$locatorValue']")
            ContentDesc -> By.xpath("//*[${buildCaseInsensitiveXpath("content-desc", locatorValue)}]")
            else -> throw error("$locatorType not a valid locator Type")
        }
    }

    private fun buildCaseInsensitiveXpath(textValue: String, partialTextSearch: Boolean = true): String {
        return if (!partialTextSearch) {
            "normalize-space(translate(@text, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) = '${textValue.toLowerCase()}'"
        } else {
            "contains(normalize-space(translate(@text, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')), '${textValue.toLowerCase()}')"
        }
    }

    private fun buildCaseInsensitiveXpath(
        attribute: String, attributeValue: String, partialTextSearch: Boolean = true
    ): String {
        return if (!partialTextSearch) {
            "normalize-space(translate(@$attribute, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) = '${attributeValue.toLowerCase()}'"
        } else {
            "contains(normalize-space(translate(@$attribute, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')), '${attributeValue.toLowerCase()}')"
        }
    }

}