package com.example.learning_english_kotlin.repository_category_entity

import com.example.learning_english_kotlin.model.CategoryEntity
import com.example.learning_english_kotlin.model.ResourcesOfCategory

interface ICategoryEntityRepository {

    fun getCategoryEntity(): List<CategoryEntity>?

    fun getResource(category: String?):List<ResourcesOfCategory>?

}