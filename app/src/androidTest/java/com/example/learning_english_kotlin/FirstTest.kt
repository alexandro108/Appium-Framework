package com.example.learning_english_kotlin
import com.example.learning_english_kotlin.appium.ViewId
import com.example.learning_english_kotlin.appium.explicitWait
import com.example.learning_english_kotlin.appium.explicitWaitClick
import com.example.learning_english_kotlin.tests.BaseAppiumTest
import io.appium.java_client.android.nativekey.AndroidKey
import io.appium.java_client.android.nativekey.KeyEvent
import org.junit.Test
import org.openqa.selenium.support.ui.WebDriverWait

class FirstTest : BaseAppiumTest(){
    @Test
    fun test(){
        device.apply {
            explicitWaitClick(ViewId.MENU)
            explicitWaitClick(ViewId.START)
            explicitWait(ViewId.PICTURE)
            explicitWaitClick(ViewId.NEXT)
        }
        }
}