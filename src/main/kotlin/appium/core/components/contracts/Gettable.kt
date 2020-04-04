package appium.core.components.contracts

interface Gettable {
    fun getAttributeValue(attributeName: String): String?
    fun getTextValue(): String?
}