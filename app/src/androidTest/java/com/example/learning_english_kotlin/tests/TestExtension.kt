package com.example.learning_english_kotlin.tests


import com.example.learning_english_kotlin.appium.AndroidAdbConfig
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Field

fun executeCommand(command: String, opt:MutableList<String>? = null): Process {
    val process = Runtime.getRuntime().exec(command)
    Thread {
        val input = BufferedReader(InputStreamReader(process.inputStream))
        var line: String? = null
        try {
            while (input.readLine().also { line = it } != null) opt?.add(line ?: "") ?: println(line)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }.start()
    Thread {
        val input = BufferedReader(InputStreamReader(process.errorStream))
        var line: String? = null
        try {
            while (input.readLine().also { line = it } != null) opt?.add(line ?: "") ?: System.err.println(line)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }.start()
    return process
}
fun executeCommandAndWait(command: String, opt:MutableList<String>? = null): Process {
    val process = executeCommand(command, opt)
    process.waitFor()
    return process
}
fun Process.killProcess(){
    val f: Field = javaClass.getDeclaredField("processHandle")
    f.isAccessible = true
    val processHandle = f.get(this)
    val pidF: Field = processHandle.javaClass.getDeclaredField("pid")
    pidF.isAccessible = true
    val pid = pidF.get(processHandle)
    Runtime.getRuntime().exec("taskkill /PID $pid")

}


fun runGradle(command:String){
    when(getOsType()) {
        OsType.WINDOWS -> {
            executeCommandAndWait("${File("").absoluteFile.parentFile?.absolutePath}/gradlew.bat $command")
        }
        OsType.MAC -> {
            executeCommandAndWait("${File("").absoluteFile.parentFile?.absolutePath}/gradlew $command")
        }
    }
}


fun startDevicesIfNotExist(multipleDevices: Boolean = false) {
    val emulators = getEmulatorsList()
    if (getActiveDeviceList().isEmpty()) {
        val e1 = System.getProperty("e1", null)
        val first = emulators.find { it == e1 } ?: emulators[0]
        runEmulator(first, "emulator-5554")
    }
    val devices = getActiveDeviceList()
    val hasFirstEmulator = devices.any { it == "emulator-5554"}
    if (multipleDevices && devices.size < 2) {
        val e2 = System.getProperty("e2")
        val second = emulators.find { it == e2 } ?: if (hasFirstEmulator) emulators[1] else emulators[0]
        runEmulator(second, if (hasFirstEmulator) "emulator-5556" else "emulator-5554")
    }
    Thread.sleep(10000)
}

private fun runEmulator(emulator: String, avd: String) {
    when (getOsType()) {
        OsType.WINDOWS -> {
            executeCommand("emulator.exe -avd $emulator")
            executeCommandAndWait("adb.exe -s $avd wait-for-device")
        }
        OsType.MAC -> {
            executeCommand("emulator -avd $emulator")
            executeCommandAndWait("adb -s $avd wait-for-device")
        }
    }
}

fun stopEmulator(emulator:String?){
    emulator ?: return
    when (getOsType()) {
        OsType.WINDOWS -> {
            executeCommandAndWait("adb.exe -s $emulator emu kill")
        }
        OsType.MAC -> {
            executeCommandAndWait("adb -s $emulator emu kill")
        }
    }
}

fun getEmulatorsList(): List<String> {
    val result = mutableListOf<String>()
    when(getOsType()) {
        OsType.WINDOWS -> {
            executeCommandAndWait("emulator.exe -list-avds",result)
        }
        OsType.MAC -> {
            executeCommandAndWait("emulator -list-avds",result)
        }
    }
    if (result.isEmpty()) {
        throw Exception("no emulators")
    }
    return result
}

fun getActiveDeviceList():List<String>{
    val result = mutableListOf<String>()
    when(getOsType()) {
        OsType.WINDOWS -> {
            executeCommandAndWait("adb.exe devices",result)
        }
        OsType.MAC -> {
            executeCommandAndWait("adb devices",result)
        }
    }
    return result.filter { it.endsWith("device") }.map { it.split("\t").first() }
}


fun getActiveAdbDevices(): List<AndroidAdbConfig> {
    val result = mutableListOf<String>()
    when(getOsType()) {
        OsType.WINDOWS -> {
            executeCommandAndWait("adb.exe devices",result)
        }
        OsType.MAC -> {
            executeCommandAndWait("adb devices",result)
        }
    }
    if (result.first() == "List of devices attached"){
        return result.drop(1).map { AndroidAdbConfig(it.split("\t").first()) }
    }
    throw Exception("device not found: ${result.joinToString()}")
}


fun getOsType(): OsType {
    val osName = System.getProperty("os.name")?.lowercase()
    return if (osName?.contains("windows") == true) OsType.WINDOWS
    else OsType.MAC
}