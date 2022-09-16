package com.example.learning_english_kotlin.main

import android.content.Context
import com.example.learning_english_kotlin.repository_theme_property.IThemePropertyRepository
import com.example.learning_english_kotlin.repository_theme_property.ThemePropertyRepository

class MainPresenter(private val view: MainContract.View, context: Context?) :
    MainContract.Presenter {

    private val repository: IThemePropertyRepository

    init {
        repository =
            ThemePropertyRepository(
                context
            )
    }

    override fun getDataFromDatabase() {
        val properties = repository.getThemeProperties()
        if (properties == null) {
            view.showMessage("Нет данных в БД")
        } else {
            view.showThemeProperties(properties)
        }
    }
}