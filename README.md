# Problem statement

Open an email app. Send email with attachment to a recipient. Validate email and attachment contents for recipient.

# Approach

- Install Yahoo mail app on android device
- Add Sender and recipient accounts
- Switch to sender account and send email with attachment
- Switch to recpient's account, open the received email and download attachment
- Pull the attachment from device to repository
- Validate the downloaded attachment content 

# Tech stack

- Automation Tool: Appium 
- Testing Framework: TestNg
- Scripting: Kotlin
- Build Tool: Gradle

# Project structure

## src/main/kotlin

- appium.core.driver: Abstraction over Android Driver. Driver Initilaization, Teardown, Starting and stoping Appium Server
- appium.core.components: Abstraction over Android UI Components for Component Interactions
- appium.core.utilities: Utility functions
- constants: Configuration and path Constants
- filehandlers: FileWriter, PdfParser and JsonParser implementations

## src/main/resources

- AppConfiguration.json: Yahoo App Configuration for Android Driver Initialization
- log4j.properties: Log4J Configuration

## src/test/kotlin

- tests/EmailValidationTests: TestNg Class with EmailValidation Test
- screens: Page Object Model Classes
- entities: Target class to deserialize the JSON data

## src/test/resources

- runlist/tests.xml: TestNg Configuration File to run tests
- EmailCredentials.json: Sender and recipient email credentials
- Attachment.pdf: Attchment for sending email

# Steps to run the test

Setup android sdk and adb , Connect an android device

## Command
./gradlew test


