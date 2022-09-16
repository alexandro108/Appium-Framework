package com.example.learning_english_kotlin.tests




import com.example.learning_english_kotlin.appium.ifAndroid
import com.example.learning_english_kotlin.rules.AppiumDriverRule
import com.example.learning_english_kotlin.rules.RetryTestRule
import io.appium.java_client.AppiumDriver
import org.junit.Rule

open class BaseAppiumTest(fullReset: Boolean = true, language: String? = null) {

    @Rule
    @JvmField
    val retryTestRule = RetryTestRule()

    @Rule
    @JvmField
    val appiumDriverRule = AppiumDriverRule(fullReset = fullReset, language = language)

    val device: AppiumDriver
        get() = appiumDriverRule.device!!.apply {

        }

}


