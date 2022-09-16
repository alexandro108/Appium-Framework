package com.example.learning_english_kotlin.appium

import io.appium.java_client.*
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.AndroidStartScreenRecordingOptions
import io.appium.java_client.appmanagement.BaseActivateApplicationOptions
import io.appium.java_client.imagecomparison.SimilarityMatchingOptions
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.ios.IOSTouchAction
import io.appium.java_client.screenrecording.CanRecordScreen
import io.appium.java_client.touch.offset.PointOption
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.lang.AssertionError
//import java.lang.management.ManagementFactory
import java.time.Duration
import java.time.temporal.TemporalUnit
import java.util.*



fun  AppiumDriver.explicitWaitClick(viewId: IViewId, driverWait: WebDriverWait? = null, optional:Boolean = false, similarityPrefix:String? = null, similarityMinScope:Double? = null){
    ifIOS { Thread.sleep(300L) }

    fun ewc() = explicitWait(viewId,driverWait,optional)?.apply {
        screenSimilarity((similarityPrefix?.let { it + "_" } ?: "") + viewId.toString(),similarityMinScope)
        Thread.sleep(500L)
        click()
    }
    if (optional){
        try {
            ewc()
        } catch (e: Exception) { }
    } else ewc()
    ifIOS { Thread.sleep(1000L) }
}

@Suppress("NewApi")
fun  AppiumDriver.explicitWait(viewId: IViewId, driverWait: WebDriverWait? = null, optional: Boolean = false): WebElement? = uiAction(optional)  {
    //ifIOS { Thread.sleep(300L) }
    when (this){
        is AndroidDriver -> {
            val currentDriverWait = driverWait ?: (this as? AndroidDevice)?.defaultWait ?: WebDriverWait(this, Duration.ofSeconds(30))
            when (viewId.androidMatchType){
                AndroidMatchType.DESCRIPTION -> currentDriverWait.explicitWaitForElementByDescription(viewId.android)
                AndroidMatchType.TEXT -> currentDriverWait.explicitWaitForElementByText(viewId.android)
                AndroidMatchType.TEXT_IGNORE_CASE -> currentDriverWait.explicitWaitForElementByText(viewId.android,true)
                AndroidMatchType.ID -> currentDriverWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("${this.capabilities.getCapability("appPackage")}:id/${viewId.android}"))
                )
                AndroidMatchType.XPATH -> {
                    currentDriverWait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath(viewId.android)))
                }
            }
        }
        //is IOSDriver -> {
        //    val currentDriverWait = driverWait ?: (this as? IOSDevice)?.defaultWait ?: WebDriverWait(this, Duration.ofSeconds(30))
        //    when (viewId.iosMatchType){
        //        IosMatchType.NAME -> findElement(By.className(viewId.ios))
        //        IosMatchType.TEXT -> currentDriverWait.explicitWaitForElementByText(viewId.ios)
        //       IosMatchType.XPATH -> currentDriverWait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.xpath(viewId.ios)))
        //        IosMatchType.ID -> currentDriverWait.until(ExpectedConditions.presenceOfElementLocated(
        //           MobileBy.AccessibilityId(viewId.ios)
        //        ))
         //       else -> throw NotImplementedError()
         //   }
        //}
        else -> throw NotImplementedError()
    }
}

fun  AppiumDriver.explicitWaitInput(input:String, viewId: IViewId, driverWait: WebDriverWait? = null, hideKeyboard:Boolean = true) = uiAction {
    explicitWait(viewId,driverWait)?.apply {
        tapOnElement(driver = this@explicitWaitInput)
        ifIOS { repeat(text.length) { sendKeys(Keys.DELETE) } }
        ifAndroid { clear() }
        sendKeys(input)
        ifAndroid { if (hideKeyboard) hideKeyboard() }
        ifIOS { sendKeys("\n") }
    }
}

//@Suppress("NewApi")
//fun  AppiumDriver.explicitWaitWithId(id: String, driverWait: WebDriverWait? = null): WebElement {
//    if (this is IOSDriver) {
//        val currentDriverWait = driverWait ?: (this as? IOSDevice)?.defaultWait ?: WebDriverWait(this, Duration.ofSeconds(30))
//        return currentDriverWait.until(ExpectedConditions.presenceOfElementLocated( By.id(id) ))
 //   }else {
//        val currentDriverWait = driverWait ?: (this as? AndroidDevice)?.defaultWait ?: WebDriverWait(this, Duration.ofSeconds(30))
//        return currentDriverWait.until(ExpectedConditions.presenceOfElementLocated( By.id(id)))
//    }
//}
//@Suppress("NewApi")
//fun  AppiumDriver.explicitWaitWithXpath(using: String, driverWait: WebDriverWait? = null): WebElement {
 //   if (this is IOSDriver) {
//        val currentDriverWait = driverWait ?: (this as? IOSDevice)?.defaultWait ?: WebDriverWait(this, Duration.ofSeconds(30))
//        return currentDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(using) ))
//    }else {
//        val currentDriverWait = driverWait ?: (this as? AndroidDevice)?.defaultWait ?: WebDriverWait(this, Duration.ofSeconds(30))
//        return currentDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(using)))
 //   }
//}

fun  AppiumDriver.findElementByViewId(viewId: IViewId, optional: Boolean = false): WebElement? = uiAction(optional) {
    when (this){
        is AndroidDriver -> {
            when (viewId.androidMatchType){
                AndroidMatchType.DESCRIPTION -> findElementByDescription(viewId.android)
                AndroidMatchType.TEXT -> findElementByText(viewId.android)
                AndroidMatchType.TEXT_IGNORE_CASE -> findElementByText(viewId.android,true)
                AndroidMatchType.ID -> findElement(
                    By.id("${this.capabilities.getCapability("appPackage")}:id/${viewId.android}")
                )
                AndroidMatchType.XPATH -> findElement(By.xpath(viewId.android))
            }
        }
        is IOSDriver -> {
            when (viewId.iosMatchType){
                IosMatchType.NAME -> findElement(By.name(viewId.ios))
                IosMatchType.TEXT -> findElement(By.xpath("//*[@name=\"${viewId.ios}\"]"))
                IosMatchType.TYPE -> findElement(By.className(viewId.ios))
                IosMatchType.XPATH -> findElement(By.xpath(viewId.ios))
                IosMatchType.ID -> findElement(By.id(viewId.ios))
            }
        }
        else -> throw NotImplementedError()

    }
}

fun WebElement.click(count:Int){
    for (i in 0 until count) click()
}

fun WebElement.tapOnElement(driver: AppiumDriver) = uiAction {
    when (driver){
        is IOSDriver -> {
            val h = this.size.height
            val w = this.size.width
            val point = PointOption.point(location.getX() + w/2, location.getY() + h/2)
            IOSTouchAction(driver as PerformsTouchActions).press(point).release().perform()
        }
        is AndroidDriver -> click()
    }
    Unit
}

fun  AppiumDriver.scrollAndClick(inScroll: IViewId, toView: IViewId, optional: Boolean = false){
    scroll(inScroll,toView,optional)?.click()
}

fun  AppiumDriver.scrollAndViewNotExist(inScroll: IViewId, toView: IViewId, optional: Boolean = false){
    try {
        scrollAndClick(inScroll, toView, optional)
        throw Error("$toView is present")
    } catch (e: Exception) {
    }

}

fun  AppiumDriver.scroll(inScroll: IViewId, toView: IViewId, optional: Boolean = false): WebElement? = uiAction(optional) {
    when (this){
        is AndroidDriver -> {
            findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(${inScroll.uiSelector()}).scrollIntoView(${toView.uiSelector()})"))
        }
        is IOSDriver -> {
            var i = 0
            while (i < 25) {
                try {
                    if (findElementByViewId(toView)?.isDisplayed == true) {
                        break
                    }
                } catch (e: Exception) { }
                i = i + 1
                val javascriptExecutor = this as JavascriptExecutor
                val scrollObject = HashMap<String, Any>()
//                val scrollObject = HashMap<String, String>()
                if (i % 3 == 0) {
//                    scrollObject["startY"] =    300.0
//                    scrollObject["endY"] =      350.0
//                    scrollObject["startX"] =    300.0
//                    scrollObject["endX"] =      300.0
//                    scrollObject["duration"] =  3.8
                    scrollObject["direction"] = "down"
                }else {
                    scrollObject["direction"] = "up"
                }

                javascriptExecutor.executeScript("mobile:swipe", scrollObject)
                Thread.sleep(1500L)
            }
            this.findElementByViewId(toView)
        }
        else -> throw NotImplementedError()
    }
}

//fun  AppiumDriver.scrollBackward(inScroll: IViewId) = uiAction {
//    ifAndroid {
//        findElement(AppiumBy.androidUIAutomator(
//            "new UiScrollable(${inScroll.uiSelector()}).scrollBackward()"));
//    }
//}

//fun  AppiumDriver.scrollToBottom(inScroll: IViewId) = uiAction {
//    when (this){
//        is AndroidDriver -> {
//            try {
//                findElement(AppiumBy.androidUIAutomator(
//                    "new UiScrollable(${inScroll.uiSelector()}).scrollToEnd(1)"))
//            } catch (e: Exception) {
//            }
//        }
//        is IOSDriver -> {
//            val javascriptExecutor = this as JavascriptExecutor
//            val scrollObject = HashMap<String, Any>()
//            scrollObject["direction"] = "up"
//
//            javascriptExecutor.executeScript("mobile:swipe", scrollObject)
//        }
//        else -> throw NotImplementedError()
//
//    }
//}
//fun  AppiumDriver.scrollToTop(inScroll: IViewId) = uiAction {
//    when (this){
 //       is AndroidDriver -> {
 ////           try {
 //               findElement(AppiumBy.androidUIAutomator(
 //                   "new UiScrollable(${inScroll.uiSelector()}).scrollToBeginning(1)"))
 //           } catch (e: Exception) {
 //           }
 //       }
 //       is IOSDriver -> {
 //           val javascriptExecutor = this as JavascriptExecutor
 //           val scrollObject = HashMap<String, Any>()
// was               val scrollObject = HashMap<String, String>()
 //           scrollObject["direction"] = "down"
//            javascriptExecutor.executeScript("mobile:swipe", scrollObject)
 //       }

 //       else -> throw NotImplementedError()
 //  }
//}

fun  AppiumDriver.screenSimilarity(screenStateId:String,similarityMinScope:Double? = null){
    if (capabilities.getCapability("screenSimilarity") as? Boolean != true) return
    Thread.sleep(600)
    val device = (capabilities.getCapability("device") as? String ?:
    capabilities.getCapability("deviceModel")  as? String ?: "d") +
            "_${capabilities.getCapability("deviceApiLevel") ?: "v"}"
    val screenName = (this as? SimilarityStates)?.flowTag?.let { "${it}_$screenStateId" } ?: screenStateId
    val compareImageFile = File("compareImages/$device/$screenName.png")
    if (compareImageFile.exists()){
        val screenshot = getScreenshotAs(OutputType.FILE)
        try {
            val result = getImagesSimilarity(screenshot,compareImageFile, SimilarityMatchingOptions().withEnabledVisualization())
            (this as? SimilarityStates)?.similarityStates?.put(screenName,result.score)
            if (result.score <= similarityMinScope ?: 0.995){
                val difft =  File("compareImages/$device/diff/${screenName}_${result.score}.png")
                difft.parentFile.mkdirs()
                result.storeVisualization(difft)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        screenshot.delete()
    }else{
        compareImageFile.parentFile.mkdirs()
        getScreenshotAs(OutputType.FILE).renameTo(compareImageFile)
    }
}

fun  AppiumDriver.flowTag(tag:String?){
    if (this is SimilarityStates) this.flowTag = tag
}
//fun  AppiumDriver.checkAppbar(tag:String){
//    Thread.sleep(1000)
//    explicitWaitClick(BankViewId.Navigation.APPBAR,similarityPrefix = "${tag.toUpperCase()}_APP")
//    Thread.sleep(1000)
//    ifAndroid { explicitWaitClick(BankViewId.Navigation.APPBAR_TITLE,similarityPrefix = "${tag.toUpperCase()}_APPBAR") }
//}

fun  AppiumDriver.ifAndroid(action:AndroidDriver.()->Unit){
    if (this is AndroidDriver) action.invoke(this)
}

fun  AppiumDriver.ifIOS(action:IOSDriver.()->Unit){
    if (this is IOSDriver) action.invoke(this)
}
fun  AppiumDriver.configuration() = (this as ConfigurationDevice).configuration

@Suppress("NewApi")
fun  AppiumDriver.startRecordingScreen() {
    try {
        (this as? CanRecordScreen)?.startRecordingScreen(AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofSeconds(1800)))
    } catch (e: Exception) {
    }
}

@Suppress("NewApi")
fun  AppiumDriver.stopRecordingScreen(outFile: File):Boolean{
    return try {
        val base64 = (this as? CanRecordScreen)?.stopRecordingScreen() ?: return false
        outFile.parentFile.mkdirs()
        outFile.writeBytes(Base64.getDecoder().decode(base64))
        true
    } catch (e: Exception) {
        false
    }
}

//fun isDebug():Boolean{
//    val isDebug = ManagementFactory.getRuntimeMXBean().inputArguments.toString().indexOf("-agentlib:jdwp") > 0
//    print(isDebug)
//    return isDebug
//}

fun <T> uiAction(optional: Boolean = false,action:()->T):T?{
   return try {
       action.invoke()
    } catch (e: Exception) {
        if (optional) return null
//       if (isDebug()){
//            e.printStackTrace()
//            null
//        }
    else{
            throw e
        }
    }
}

fun AppiumDriver.assertViewNotExist(viewId: IViewId, @Suppress("NewApi") driverWait: WebDriverWait = WebDriverWait(this, Duration.ofSeconds(5))) {
    if (explicitWait(viewId, optional = true, driverWait = driverWait) != null) {
        throw AssertionError()
    }
}

fun AppiumDriver.resetApp() {
    ifAndroid {
        resetApp()
    }
    ifIOS {
        resetApp()
    }
}

fun AppiumDriver.openApp(){
    when (this){
        is AndroidDriver -> {
            executeScript("mobile: shell", mapOf(
                "command" to "am start",
                "args" to arrayOf(
                    "-W",
                    "-n", "${capabilities.getCapability("appPackage") as String}/${capabilities.getCapability("appActivity") as String}",
                    "-S", "-a", "android.intent.action.MAIN", "-c", "android.intent.category.LAUNCHER", "-f", "0x10200000", "--ez autotest true"
                )
            ))
        }
        is IOSDriver -> {
            activateApp(capabilities.getCapability("bundleId") as String)
        }
        else -> throw NotImplementedError()
    }
}


