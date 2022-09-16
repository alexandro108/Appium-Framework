package com.example.learning_english_kotlin.rules

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RetryTestRule(val retryCount: Int = 1) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return statement(base)
    }

    private fun statement(base: Statement): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                var caughtThrowable: Throwable? = null
                for (i in 0 until retryCount) {
                    try {
                        base.evaluate()
                        return
                    } catch (t: Throwable) {
                        caughtThrowable = t
                    }
                }
                throw caughtThrowable!!
            }
        }
    }
}
