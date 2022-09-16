package com.example.learning_english_kotlin.repository_category_entity

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.learning_english_kotlin.DBHelper
import com.example.learning_english_kotlin.model.CategoryEntity
import com.example.learning_english_kotlin.model.ResourcesOfCategory

class CategoryEntityRepository(context: Context?) : ICategoryEntityRepository {

    private val dbHelper: DBHelper = DBHelper(context)
    var db: SQLiteDatabase

    init {
        db = dbHelper.writableDatabase
    }

    override fun getCategoryEntity(): List<CategoryEntity>? {
        val cursor = db.query("LESSON", null, null, null, null, null, null)
        val entities = ArrayList<CategoryEntity>()
        return if (cursor.moveToFirst()) {
            val idColIndex = cursor.getColumnIndex("_id")
            val wordRuIndex = cursor.getColumnIndex("WORD_RU")
            val wordEnIndex = cursor.getColumnIndex("WORD_EN")
            val categoryIndex = cursor.getColumnIndex("CATEGORY")
            val imageResourceIdIndex = cursor.getColumnIndex("IMAGE_RESOURCE_ID")
            do {
                val id = cursor.getInt(idColIndex)
                val wordRu = cursor.getString(wordRuIndex)
                val wordEn = cursor.getString(wordEnIndex)
                val category = cursor.getString(categoryIndex)
                val imageResourceId = cursor.getInt(imageResourceIdIndex)
                val categoryEntity = CategoryEntity(
                    id,
                    wordEn,
                    wordRu,
                    category,
                    imageResourceId
                )
                entities.add(categoryEntity)
            } while (cursor.moveToNext())
            cursor.close()
            entities
        } else {
           null
        }
    }

    override fun getResource(category: String?): List<ResourcesOfCategory>? {
        val cursor = dbHelper.readableDatabase.rawQuery(
            "select * from LESSON where CATEGORY = ?",
            arrayOf(category)
        )
        val resources = ArrayList<ResourcesOfCategory>()
        return if (cursor.moveToFirst()) {
            do {
                val wordRus = cursor.getString(1)
                val wordEng = cursor.getString(2)
                val imageResourceId = cursor.getInt(4)
                val resourcesOfCategory =
                    ResourcesOfCategory(wordRus, wordEng, category, imageResourceId)
                resources.add(resourcesOfCategory)
            } while (cursor.moveToNext())
            cursor.close()
            resources
        } else {
            null
        }
    }
}