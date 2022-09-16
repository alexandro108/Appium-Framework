package com.example.learning_english_kotlin.model

class CategoryEntity {
    var id = 0
    private var word_ru: String? = null
    private var word_en: String? = null
    var category: String?=null
    var imageResourceId = 0

    constructor(id: Int, word_ru: String?, word_en: String?, category: String?, imageResourceId: Int) {
        this.id = id
        this.word_ru = word_ru
        this.word_en = word_en
        this.category = category
        this.imageResourceId = imageResourceId
    }

    constructor(category: String?) {
        this.category = category
    }
}