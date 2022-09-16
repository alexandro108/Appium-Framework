package com.example.learning_english_kotlin.tests
import com.example.learning_english_kotlin.FirstTest
import org.junit.ClassRule
import org.junit.runner.RunWith
import org.junit.runners.Suite
@RunWith(Suite::class)
@Suite.SuiteClasses(
    FirstTest::class,
)
class AppiumTestStart {
    companion object {
        @ClassRule
        @JvmField
        val setupClassRule = SetupClassRule(false)
    }
}