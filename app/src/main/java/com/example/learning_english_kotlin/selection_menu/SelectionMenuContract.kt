package com.example.learning_english_kotlin.selection_menu

import com.example.learning_english_kotlin.model.CategoryEntity
import com.example.learning_english_kotlin.model.ResourcesOfCategory
import com.example.learning_english_kotlin.model.ThemeProperty

interface SelectionMenuContract {

    interface View {

        fun showThemeProperty(properties: ThemeProperty)

        fun showMessage(message: String?)

        fun showCategoryEntities(entities: List<CategoryEntity>)

        fun showLesson(categoryEntity: CategoryEntity)

        fun showResources(resources: List<ResourcesOfCategory>)

    }

    interface Presenter {

        fun getThemePropertyFromDatabase(id: Int)

        fun getResourcesFromDatabase(category: String?)

    }
}