package com.example.learning_english_kotlin.tests

import org.json.JSONObject
import org.junit.rules.ExternalResource
import java.io.File

class SetupClassRule(private val multipleDevices: Boolean = false): ExternalResource() {

    var file: File? = null

    override fun before() {
        startDevicesIfNotExist(multipleDevices)

        val devices = getActiveAdbDevices()
        val config = JSONObject().apply {

            put("platform", "android")
            devices.getOrNull(0)?.let {
                put("primaryDevice", JSONObject().apply {
                    put("udid", it.udid)
                })
            }
            devices.getOrNull(1)?.let {
                put("secondaryDevice", JSONObject().apply {
                    put("udid", it.udid)
                })
            }
        }
        val fileConfig = File("build/conf.json")
        fileConfig.parentFile?.mkdirs()
        fileConfig.writeText(config.toString())
        System.setProperty("testConfigFile",fileConfig.absolutePath)
        file = fileConfig
    }

    override fun after() {
        val stopEmulator = System.getProperty("stopEmulator", "false").toBoolean()
        if (stopEmulator) getActiveDeviceList().forEach {
            //if (it.startsWith("emulator")) stopEmulator(it)
        }
        file?.delete()
    }
}