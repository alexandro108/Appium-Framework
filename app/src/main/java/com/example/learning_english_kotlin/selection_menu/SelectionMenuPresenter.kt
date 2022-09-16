package com.example.learning_english_kotlin.selection_menu

import android.content.Context
import com.example.learning_english_kotlin.repository_category_entity.CategoryEntityRepository
import com.example.learning_english_kotlin.repository_category_entity.ICategoryEntityRepository
import com.example.learning_english_kotlin.repository_theme_property.IThemePropertyRepository
import com.example.learning_english_kotlin.repository_theme_property.ThemePropertyRepository

class SelectionMenuPresenter(private val view: SelectionMenuContract.View, context: Context?) :
    SelectionMenuContract.Presenter {

    private val repository: IThemePropertyRepository
    private val categoryEntityRepository: ICategoryEntityRepository

    init {
        repository =
            ThemePropertyRepository(
                context
            )
        categoryEntityRepository = CategoryEntityRepository(context)
    }


    override fun getThemePropertyFromDatabase(id: Int) {
        val properties = repository.getThemeProperty(id)
        if (properties == null) {
            view.showMessage("Свойство темы не найдено. ID = $id")
        } else {
            view.showThemeProperty(properties)
        }
    }

    override fun getResourcesFromDatabase(category: String?) {
        val resourceList = categoryEntityRepository.getResource(category)
        if (resourceList == null) {
            view.showMessage("Нет ресурсов в БД.")
        } else {
            view.showResources(resourceList)
        }
    }
}