package com.example.learning_english_kotlin.appium


import io.appium.java_client.AppiumBy
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL

//const val USERNAME = ""
//const val AUTOMATE_KEY = ""
const val USERNAME = ""
const val AUTOMATE_KEY = ""
const val URL = "https://$USERNAME:$AUTOMATE_KEY@hub-cloud.browserstack.com/wd/hub"
const val URL_D = "http://hub.browserstack.com/wd/hub"


interface AppiumConfiguration {
    fun initDevice(configuration: TestConfiguration, bundleId:String,fullReset:Boolean = true, language: String? = null): AppiumDriver
}

data class AndroidAdbConfig(
    val udid: String
) : AppiumConfiguration {
    override fun initDevice(configuration: TestConfiguration, bundleId:String,fullReset:Boolean, language: String?): AppiumDriver {
        val cap = DesiredCapabilities().apply {
            setCapability("udid", udid)
            setCapability("appPackage", bundleId)
            setCapability("appActivity", "com.app.banking.splash.SplashActivity")
            setCapability("autoGrantPermissions", "true")
            setCapability("optionalIntentArguments", "--ez autotest true")
            setCapability("newCommandTimeout", 999)
            setCapability("screenSimilarity", false)
        }

        if (!fullReset) cap.apply {
            setCapability("noReset", "true")
            setCapability("fullReset", "false")
        }
        return AndroidDevice(
            URL("http://0.0.0.0:4723/wd/hub"),
            cap,
            configuration
        )
    }

}

data class AndroidBrowserstackConfig(
  val appPackage: String,
    override val app: String,
    override val project: String,
    override val build: String,
    override val name: String,
    override val device: TestDevice
) :BrowserstackConfig, AppiumConfiguration {

    override fun initDevice(configuration: TestConfiguration, bundleId:String,fullReset:Boolean, language: String?): AppiumDriver {
        return AndroidDevice(
            URL(URL),
            DesiredCapabilities().apply {
                applyToCapabilities(this)
                setCapability("browserName", "android")
                setCapability("realMobile", "true")
                setCapability("appPackage", appPackage)
                setCapability("appActivity", "com.app.banking.splash.SplashActivity")
                setCapability("autoGrantPermissions", "true")
                setCapability("optionalIntentArguments", "--ez autotest true")
                setCapability("newCommandTimeout",   999)
                setCapability("browserstack.networkLogs", "true")
                if (!fullReset){
                    setCapability("noReset", "true")
                    setCapability("fullReset", "false")
                }
            },
            configuration
        )
    }

}


fun IViewId.uiSelector():String{
    return StringBuilder("new UiSelector()").apply {
        val id = this@uiSelector.android
        append(when (this@uiSelector.androidMatchType){
            AndroidMatchType.ID -> ".resourceIdMatches(\".*$id.*\")"
            AndroidMatchType.DESCRIPTION -> ".description(\"$id\")"
            AndroidMatchType.TEXT -> ".textMatches(\"$id\")"
            AndroidMatchType.TEXT_IGNORE_CASE ->
                ".textMatches(\"${id.toCharArray().joinToString(separator = "") {
                    "[${it.toUpperCase()}${it.toLowerCase()}]"
                }}\")"
            AndroidMatchType.XPATH -> ".xpath(\"$id\")"
        })
    }.toString()
}

fun focusedSelector() = "new UiSelector().focused(true)"

fun AndroidDriver.findElementByText(text:String,ignoreCase:Boolean = false): WebElement{
    val regex = if (ignoreCase){
        text.toCharArray().joinToString(separator = "") {
            "[${it.toUpperCase()}${it.toLowerCase()}]"
        }
    }else text
    return findElement(AppiumBy.androidUIAutomator("new UiSelector().textMatches(\"$regex\")"))
}

fun WebDriverWait.explicitWaitForElementByText(text:String,ignoreCase:Boolean = false): WebElement {
    return until(presenceOfElementWithTextMatches(text,ignoreCase))
}

fun presenceOfElementWithTextMatches(text:String,ignoreCase:Boolean = false): ExpectedCondition<WebElement> {
    return object : ExpectedCondition<WebElement> {
        override fun apply(driver: WebDriver?): WebElement? {
//            return (driver as? AndroidDriver)?.findElementByText(text,ignoreCase)
            var result = (driver as? AndroidDriver)?.findElementByText(text, ignoreCase)
            if (result != null) {
                return result
            } else {
                return (driver as? IOSDriver)?.findElement(By.xpath("//*[@name=\"${text}\"]"))
            }
        }

        override fun toString(): String {
            return "presence of element with text: $text"
        }
    }
}

fun AndroidDriver.findElementByDescription(description:String): WebElement{
    return findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"$description\")"))
}


fun WebDriverWait.explicitWaitForElementByDescription(description:String): WebElement {
    return until(presenceOfElementWithDescription(description))
}

fun presenceOfElementWithDescription(description:String): ExpectedCondition<WebElement> {
    return object : ExpectedCondition<WebElement> {
        override fun apply(driver: WebDriver?): WebElement? {
            return (driver as? AndroidDriver)?.findElementByDescription(description)
        }

        override fun toString(): String {
            return "presence of element with description: $description"
        }
    }
}
