package com.example.learning_english_kotlin.appium


import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.Capabilities
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.time.Duration
import java.time.temporal.TemporalUnit

@Suppress("NewApi")
class AndroidDevice(
    remoteAddress: URL,
    desiredCapabilities: Capabilities,
    override val configuration: TestConfiguration
) :AndroidDriver(remoteAddress, desiredCapabilities), SimilarityStates,
    ConfigurationDevice {

    override val similarityStates by lazy { mutableMapOf<String,Double>() }

    override var flowTag:String? = null

    val defaultWait by lazy { WebDriverWait(this, Duration.ofSeconds(30)) }

}

interface SimilarityStates {
    var flowTag:String?
    val similarityStates: MutableMap<String, Double>
}
