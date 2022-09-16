package com.example.learning_english_kotlin.adapter

import com.example.learning_english_kotlin.model.ThemeProperty

interface OnItemClickListener {
    fun onItemClickedCategory(themeProperty: ThemeProperty)
}