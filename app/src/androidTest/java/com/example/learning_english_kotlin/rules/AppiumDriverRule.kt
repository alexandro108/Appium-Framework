package com.example.learning_english_kotlin.rules

import com.example.learning_english_kotlin.appium.AndroidAdbConfig
import com.example.learning_english_kotlin.tests.executeCommandAndWait
import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.ios.IOSStartScreenRecordingOptions
import io.appium.java_client.screenrecording.CanRecordScreen
import org.json.JSONObject
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.io.File
import java.time.Duration
import java.util.*
import kotlin.random.Random.Default.nextInt

class AppiumDriverRule(
    private val fullReset: Boolean,
    private val deviceNumber: String = "primaryDevice",
    private val multipleDeviceTest:Boolean = false,
    private val language: String? = null,
) : TestRule {

    var device: AppiumDriver? = null
    var description: Description? = null
    private var recordProcess: Process? = null
    private var isTestFail: Boolean = false
    private val testName: String
        get() {
            val name =  description?.let { it.testClass.simpleName + "_" + it.methodName + (if (multipleDeviceTest) "_$deviceNumber" else "") }
                ?: ("unknownTest" + nextInt().toString())
            return name.replace(" ","_")
        }
    private var recordName: String = "unknownRecord" + nextInt().toString()

    private fun setNewRecordName() {
        val name =  (description?.let {
            it.testClass.simpleName + "_" + it.methodName + (if (multipleDeviceTest) "_$deviceNumber" else "") + "_${System.currentTimeMillis()}"
        } ?: ("unknownRecord" + nextInt().toString()))
        recordName = name.replace(" ","_")
    }

    override fun apply(base: Statement, description: Description): Statement {
        this.description = description
        return object : Statement() {
            override fun evaluate() {

                try {
                    base.evaluate()
                    isTestFail = false
                } catch (t: Throwable){
                    isTestFail = true
                    throw t
                } finally {

                }
            }
        }
    }

    private fun getPlatformConfiguration(): TestPlatformConfiguration {
        fun defaultConfig(): String {
            val steam = javaClass.classLoader.getResourceAsStream("testConfig.json")
                ?: throw RuntimeException("config file not found")
            return steam.bufferedReader().use { it.readText() }
        }

        val configString =
            System.getProperty("testConfigFile")?.let(::File)?.readText() ?: defaultConfig()
        return TestPlatformConfiguration(JSONObject(configString), deviceNumber)
    }

data class TestPlatformConfiguration(
    val cluster: String,
    val env: String,
    val bundleId: String,
    val platform: String,
    val udid: String,
    val app: String?
) {
    constructor(json: JSONObject, deviceNumber: String) : this(
        cluster = json.getString("cluster"),
        env = json.getString("env"),
        bundleId = json.getString("bundleId"),
        platform = json.getString("platform"),
        udid = json.getJSONObject(deviceNumber).getString("udid"),
        app = json.opt("app") as? String
    )
}
}
