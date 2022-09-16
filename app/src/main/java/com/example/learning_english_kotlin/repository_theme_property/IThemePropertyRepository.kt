package com.example.learning_english_kotlin.repository_theme_property

import com.example.learning_english_kotlin.model.ThemeProperty

interface IThemePropertyRepository {

    fun getThemeProperty(themeId: Int): ThemeProperty?

    fun getThemeProperties(): List<ThemeProperty>?

}