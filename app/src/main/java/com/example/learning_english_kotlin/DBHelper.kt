package com.example.learning_english_kotlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        updateMyDatabase(db, 0, VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        updateMyDatabase(db, 0, VERSION)
    }

    private fun insertThemeProperty(db: SQLiteDatabase, typeOfTheme: String, countOfWords: String) {
        val themeValues = ContentValues()
        themeValues.put("TYPE_OF_THEME", typeOfTheme)
        themeValues.put("COUNT_OF_WORDS", countOfWords)
        db.insert("THEME", null, themeValues)
    }

    private fun insertLesson(
        db: SQLiteDatabase,
        wordRu: String,
        wordEn: String,
        category: String,
        resourceId: Int
    ) {
        val lesson = ContentValues()
        lesson.put("WORD_RU", wordRu)
        lesson.put("WORD_EN", wordEn)
        lesson.put("CATEGORY", category)
        lesson.put("IMAGE_RESOURCE_ID", resourceId)
        db.insert("LESSON", null, lesson)
    }

    private fun updateMyDatabase(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 1) {
            db.execSQL(
                "CREATE TABLE THEME(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "TYPE_OF_THEME TEXT,"
                        + "COUNT_OF_WORDS TEXT);"
            )

            insertThemeProperty(db, "Семья и друзья", "9")
            insertThemeProperty(db, "Части тела", "9")
            insertThemeProperty(db, "Одежда", "8")
            insertThemeProperty(db, "Цифры", "13")
            insertThemeProperty(db, "Цвета", "10")
            insertThemeProperty(db, "Фрукты и овощи", "10")
            insertThemeProperty(db, "Еда", "13")
            insertThemeProperty(db, "Школа", "8")
            insertThemeProperty(db, "Погода и природа", "15")
            insertThemeProperty(db, "Животные", "10")

            db.execSQL(
                "CREATE TABLE LESSON(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "WORD_RU TEXT,"
                        + "WORD_EN TEXT,"
                        + "CATEGORY TEXT,"
                        + "IMAGE_RESOURCE_ID INTEGER);"
            )

            insertLesson(db, "мама", "mother", "Семья и друзья", R.drawable.mother)
            insertLesson(db, "отец", "father", "Семья и друзья", R.drawable.father)
            insertLesson(db, "брат", "brother", "Семья и друзья", R.drawable.brother)
            insertLesson(db, "сестра", "sister", "Семья и друзья", R.drawable.sister)
            insertLesson(db, "сын", "son", "Семья и друзья", R.drawable.son)
            insertLesson(db, "дочь", "daughter", "Семья и друзья", R.drawable.daughter)
            insertLesson(db, "бабушка", "grandmother", "Семья и друзья", R.drawable.grandmother)
            insertLesson(db, "дед", "grandfather", "Семья и друзья", R.drawable.grandfather)
            insertLesson(db, "семья", "family", "Семья и друзья", R.drawable.family)


            insertLesson(db, "глаз", "eye", "Части тела", R.drawable.eye)
            insertLesson(db, "нос", "nose", "Части тела", R.drawable.nose)
            insertLesson(db, "рот", "mouth", "Части тела", R.drawable.mouth)
            insertLesson(db, "нога", "leg", "Части тела", R.drawable.leg)
            insertLesson(db, "голова", "head", "Части тела", R.drawable.head)
            insertLesson(db, "зуб", "tooth", "Части тела", R.drawable.tooth)
            insertLesson(db, "рука", "arm", "Части тела", R.drawable.arm)
            insertLesson(db, "ухо", "ear", "Части тела", R.drawable.ear)
            insertLesson(db, "губа", "lip", "Части тела", R.drawable.lip)

            insertLesson(db, "джинсы", "jeans", "Одежда", R.drawable.jeans)
            insertLesson(db, "шорты", "shorts", "Одежда", R.drawable.shorts)
            insertLesson(db, "платье", "dress", "Одежда", R.drawable.dress)
            insertLesson(db, "футболка", "T-shirt", "Одежда", R.drawable.tshirt)
            insertLesson(db, "рубашка", "shirt", "Одежда", R.drawable.shirt)
            insertLesson(db, "кепка", "cap", "Одежда", R.drawable.cap)
            insertLesson(db, "свитер", "pullover", "Одежда", R.drawable.pullover)
            insertLesson(db, "пальто", "coat", "Одежда", R.drawable.coat)

            insertLesson(db, "ноль", "zero", "Цифры", R.drawable.zero)
            insertLesson(db, "один", "one", "Цифры", R.drawable.one)
            insertLesson(db, "два", "two", "Цифры", R.drawable.two)
            insertLesson(db, "три", "three", "Цифры", R.drawable.three)
            insertLesson(db, "четыре", "four", "Цифры", R.drawable.four)
            insertLesson(db, "пять", "five", "Цифры", R.drawable.five)
            insertLesson(db, "шесть", "six", "Цифры", R.drawable.six)
            insertLesson(db, "семь", "seven", "Цифры", R.drawable.seven)
            insertLesson(db, "восемь", "eight", "Цифры", R.drawable.eight)
            insertLesson(db, "девять", "nine", "Цифры", R.drawable.nine)
            insertLesson(db, "десять", "ten", "Цифры", R.drawable.ten)
            insertLesson(db, "одиннадцать", "eleven", "Цифры", R.drawable.eleven)
            insertLesson(db, "двенадцать", "twelve", "Цифры", R.drawable.twelve)

            insertLesson(db, "желтый", "yellow", "Цвета", R.drawable.yellow)
            insertLesson(db, "зеленый", "green", "Цвета", R.drawable.green)
            insertLesson(db, "синий", "blue", "Цвета", R.drawable.blue)
            insertLesson(db, "коричневый", "brown", "Цвета", R.drawable.brown)
            insertLesson(db, "белый", "white", "Цвета", R.drawable.white)
            insertLesson(db, "красный", "red", "Цвета", R.drawable.red)
            insertLesson(db, "оранжевый", "orange", "Цвета", R.drawable.orange)
            insertLesson(db, "розовый", "pink", "Цвета", R.drawable.pink)
            insertLesson(db, "серый", "gray", "Цвета", R.drawable.gray)
            insertLesson(db, "черный", "black", "Цвета", R.drawable.black)

            insertLesson(db, "яблоко", "apple", "Фрукты и овощи", R.drawable.apple)
            insertLesson(db, "лимон", "lemon", "Фрукты и овощи", R.drawable.lemon)
            insertLesson(db, "банан", "banana", "Фрукты и овощи", R.drawable.banana)
            insertLesson(db, "дыня", "melon", "Фрукты и овощи", R.drawable.melon)
            insertLesson(db, "клубника", "strawberry", "Фрукты и овощи", R.drawable.strawberry)
            insertLesson(db, "картошка", "potato", "Фрукты и овощи", R.drawable.potato)
            insertLesson(db, "помидор", "tomato", "Фрукты и овощи", R.drawable.tomato)
            insertLesson(db, "морковь", "carrot", "Фрукты и овощи", R.drawable.carrot)
            insertLesson(db, "огурец", "cucumber", "Фрукты и овощи", R.drawable.cucumber)
            insertLesson(db, "перец", "pepper", "Фрукты и овощи", R.drawable.pepper)

            insertLesson(db, "бутерброд", "sandwich", "Еда", R.drawable.sandwich)
            insertLesson(db, "торт", "cake", "Еда", R.drawable.cake)
            insertLesson(db, "чай", "tea", "Еда", R.drawable.tea)
            insertLesson(db, "кофе", "coffee", "Еда", R.drawable.coffee)
            insertLesson(db, "сахар", "sugar", "Еда", R.drawable.sugar)
            insertLesson(db, "сыр", "cheese", "Еда", R.drawable.cheese)
            insertLesson(db, "соль", "salt", "Еда", R.drawable.salt)
            insertLesson(db, "курица", "hen", "Еда", R.drawable.chicken)
            insertLesson(db, "сосиска", "sausage", "Еда", R.drawable.sausage)
            insertLesson(db, "мясо", "meat", "Еда", R.drawable.meat)
            insertLesson(db, "рыба", "fish", "Еда", R.drawable.fish)
            insertLesson(db, "хлеб", "bread", "Еда", R.drawable.bread)
            insertLesson(db, "молоко", "milk", "Еда", R.drawable.milk)

            insertLesson(db, "парта", "desk", "Школа", R.drawable.desk)
            insertLesson(db, "книга", "book", "Школа", R.drawable.book)
            insertLesson(db, "карандаш", "pencil", "Школа", R.drawable.pencil)
            insertLesson(db, "ручка", "pen", "Школа", R.drawable.pen)
            insertLesson(db, "краска", "paint", "Школа", R.drawable.paint)
            insertLesson(db, "линейка", "ruler", "Школа", R.drawable.ruler)
            insertLesson(db, "бумага", "paper", "Школа", R.drawable.paper)
            insertLesson(db, "карта", "map", "Школа", R.drawable.map)

            insertLesson(db, "небо", "sky", "Погода и природа", R.drawable.sky)
            insertLesson(db, "звезда", "star", "Погода и природа", R.drawable.star)
            insertLesson(db, "солнце", "sun", "Погода и природа", R.drawable.sun)
            insertLesson(db, "луна", "moon", "Погода и природа", R.drawable.moon)
            insertLesson(db, "облако", "cloud", "Погода и природа", R.drawable.cloud)
            insertLesson(db, "молния", "lightning", "Погода и природа", R.drawable.lightning)
            insertLesson(db, "дождь", "rain", "Погода и природа", R.drawable.rain)
            insertLesson(db, "снег", "snow", "Погода и природа", R.drawable.snow)
            insertLesson(db, "ветер", "wind", "Погода и природа", R.drawable.wind)
            insertLesson(db, "тепло", "heat", "Погода и природа", R.drawable.heat)
            insertLesson(db, "холод", "cold", "Погода и природа", R.drawable.cold)
            insertLesson(db, "море", "sea", "Погода и природа", R.drawable.sea)
            insertLesson(db, "озеро", "lake", "Погода и природа", R.drawable.lake)
            insertLesson(db, "река", "river", "Погода и природа", R.drawable.river)
            insertLesson(db, "трава", "grass", "Погода и природа", R.drawable.grass)

            insertLesson(db, "кот", "cat", "Животные", R.drawable.cat)
            insertLesson(db, "корова", "cow", "Животные", R.drawable.cow)
            insertLesson(db, "собака", "dog", "Животные", R.drawable.dog)
            insertLesson(db, "утка", "duck", "Животные", R.drawable.duck)
            insertLesson(db, "лошадь", "horse", "Животные", R.drawable.horse)
            insertLesson(db, "мышь", "mouse", "Животные", R.drawable.mouse)
            insertLesson(db, "свинья", "pig", "Животные", R.drawable.pig)
            insertLesson(db, "медведь", "bear", "Животные", R.drawable.bear)
            insertLesson(db, "слон", "elephant", "Животные", R.drawable.elephant)
            insertLesson(db, "обезьяна", "monkey", "Животные", R.drawable.monkey)

        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE THEME ADD COLUMN FAVORITE NUMERIC;")
            db.execSQL("ALTER TABLE LESSON ADD COLUMN FAVORITE NUMERIC;")
        }
    }

    companion object {
        const val DATABASE_NAME = "English_self-study_guide"
        const val VERSION = 2
    }
}