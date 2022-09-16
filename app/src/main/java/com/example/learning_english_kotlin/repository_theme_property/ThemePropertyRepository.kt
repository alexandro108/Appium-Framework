package com.example.learning_english_kotlin.repository_theme_property

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.learning_english_kotlin.DBHelper
import com.example.learning_english_kotlin.model.ThemeProperty

class ThemePropertyRepository(context: Context?) :
    IThemePropertyRepository {

    private val dbHelper: DBHelper = DBHelper(context)
    var db: SQLiteDatabase

    init {
        db = dbHelper.writableDatabase
    }


    override fun getThemeProperty(themeId: Int): ThemeProperty? {
        db = dbHelper.readableDatabase
        val cursor = db.query(
            "THEME",
            arrayOf("TYPE_OF_THEME", "COUNT_OF_WORDS"), "_id=?",
            arrayOf(themeId.toString()), null, null, null
        )
        return if (cursor.moveToFirst()) {
            val typeOfTheme = cursor.getString(0)
            val countOfWords = cursor.getString(1)
            val themeProperty =
                ThemeProperty(
                    themeId,
                    typeOfTheme,
                    countOfWords
                )
            cursor.close()
            themeProperty
        } else {
            null
        }

    }

    override fun getThemeProperties(): List<ThemeProperty>? {
        val cursor = db.query("THEME", null, null, null, null, null, null)
        val properties = ArrayList<ThemeProperty>()
        return if (cursor.moveToFirst()) {
            val idColIndex = cursor.getColumnIndex("_id")
            val typeOfThemeIndex = cursor.getColumnIndex("TYPE_OF_THEME")
            val countOfWordsIndex = cursor.getColumnIndex("COUNT_OF_WORDS")
            do {
                val id = cursor.getInt(idColIndex)
                val typeOfTheme = cursor.getString(typeOfThemeIndex)
                val countOfWords = cursor.getString(countOfWordsIndex)
                val themeProperty =
                    ThemeProperty(
                        id,
                        typeOfTheme,
                        countOfWords
                    )
                properties.add(themeProperty)
            } while (cursor.moveToNext())
            cursor.close()
            properties
        } else {
            null
        }
    }
}