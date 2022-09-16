package com.example.learning_english_kotlin.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learning_english_kotlin.*
import com.example.learning_english_kotlin.adapter.CategoryAdapter
import com.example.learning_english_kotlin.adapter.OnItemClickListener
import com.example.learning_english_kotlin.model.Category
import com.example.learning_english_kotlin.model.ThemeProperty
import com.example.learning_english_kotlin.selection_menu.SelectionMenuFragment
import java.util.ArrayList


class MainActivity : AppCompatActivity(),
    MainContract.View {

    private lateinit var categories: MutableList<Category>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<View>(R.id.rvCategories) as RecyclerView
        categories = ArrayList()
        (categories as ArrayList<Category>).add(
            Category(
                "Семья и друзья",
                R.drawable.family_and_friends
            )
        )
        (categories as ArrayList<Category>).add(
            Category(
                "Части тела",
                R.drawable.body_parts
            )
        )
        (categories as ArrayList<Category>).add(
            Category(
                "Одежда",
                R.drawable.clothes
            )
        )
        (categories as ArrayList<Category>).add(
            Category(
                "Цифры",
                R.drawable.digits
            )
        )
        (categories as ArrayList<Category>).add(
            Category(
                "Цвета",
                R.drawable.colors
            )
        )
        (categories as ArrayList<Category>).add(
            Category(
                "Фрукты и овощи",
                R.drawable.fruits_and_vegetables
            )
        )
        (categories as ArrayList<Category>).add(
            Category(
                "Еда",
                R.drawable.food
            )
        )
        (categories as ArrayList<Category>).add(
            Category(
                "Школа",
                R.drawable.school
            )
        )
        (categories as ArrayList<Category>).add(
            Category(
                "Погода и природа",
                R.drawable.weather
            )
        )
        (categories as ArrayList<Category>).add(
            Category(
                "Животные",
                R.drawable.animals
            )
        )
        adapter = CategoryAdapter(
            categories as ArrayList<Category>
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        presenter =
            MainPresenter(
                this,
                baseContext
            )
        presenter.getDataFromDatabase()
        adapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClickedCategory(themeProperty: ThemeProperty) {
                showSelectionMenu(themeProperty)
            }
        })
    }

    override fun showMessage(message: String?) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showThemeProperties(properties: List<ThemeProperty>) {
        adapter.setThemePropertyList(properties)
    }

    override fun showSelectionMenu(property: ThemeProperty) {
        val bundle = Bundle()
        bundle.putInt("themeId", property.id)
        val fragment =
            SelectionMenuFragment()
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}