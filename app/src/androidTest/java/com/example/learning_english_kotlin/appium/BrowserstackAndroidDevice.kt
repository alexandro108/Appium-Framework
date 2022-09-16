package com.example.learning_english_kotlin.appium


import org.openqa.selenium.remote.DesiredCapabilities


interface BrowserstackConfig{
    val app:String
    val project:String
    val build:String
    val name:String
    val device: TestDevice

    fun applyToCapabilities(capabilities: DesiredCapabilities){
        capabilities.apply {
            setCapability("device", device.deviceName)
            setCapability("os_version", device.osVersion)
            setCapability("project", project)
            setCapability("build", build)
            setCapability("name", name)
        }
    }
}

class TestDevice(val deviceName:String,val osVersion:String) {}