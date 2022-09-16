package com.example.learning_english_kotlin.main

import com.example.learning_english_kotlin.model.ThemeProperty

interface MainContract {

    interface View {

        fun showMessage(message: String?)

        fun showThemeProperties(properties: List<ThemeProperty>)

        fun showSelectionMenu(property: ThemeProperty)

    }

    interface Presenter {

        fun getDataFromDatabase()

    }
}