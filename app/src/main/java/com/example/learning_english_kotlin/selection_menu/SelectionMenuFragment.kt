package com.example.learning_english_kotlin.selection_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.learning_english_kotlin.R
import com.example.learning_english_kotlin.lesson.LessonFragment
import com.example.learning_english_kotlin.model.CategoryEntity
import com.example.learning_english_kotlin.model.ResourcesOfCategory
import com.example.learning_english_kotlin.model.ThemeProperty

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectionMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectionMenuFragment : Fragment(), SelectionMenuContract.View {
    // TODO: Rename and change types of parameters
    private lateinit var fragmentView: View
    private lateinit var presenter: SelectionMenuPresenter
    private lateinit var categoryEntity: CategoryEntity
    lateinit var category: String

    var resources = ArrayList<ResourcesOfCategory>()

    var entities = ArrayList<CategoryEntity>()

    private fun setEntities(entities: List<CategoryEntity>) {
        this.entities = entities as ArrayList<CategoryEntity>
    }

    private fun setResources(resources: List<ResourcesOfCategory>) {
        this.resources = resources as ArrayList<ResourcesOfCategory>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = SelectionMenuPresenter(
            this,
            context
        )
        val themeId = arguments?.getInt("themeId")
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_selection_menu, null)
        when (themeId) {
            1 -> category = "Семья и друзья"
            2 -> category = "Части тела"
            3 -> category = "Одежда"
            4 -> category = "Цифры"
            5 -> category = "Цвета"
            6 -> category = "Фрукты и овощи"
            7 -> category = "Еда"
            8 -> category = "Школа"
            9 -> category = "Погода и природа"
            10 -> category = "Животные"
        }
        categoryEntity = CategoryEntity(category)
        if (themeId != null) {
            presenter.getThemePropertyFromDatabase(themeId)
        }
        presenter.getResourcesFromDatabase(category)
        val btnLesson = fragmentView.findViewById<View>(R.id.btn_start_learning) as Button
        btnLesson.setOnClickListener { showLesson(categoryEntity) }
        return fragmentView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SelectionMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectionMenuFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun showThemeProperty(properties: ThemeProperty) {
        val typeOfTheme = fragmentView.findViewById<View>(R.id.tvTheme) as TextView
        typeOfTheme.text = properties.typeOfTheme
        val countOfWords = fragmentView.findViewById<View>(R.id.count_of_words) as TextView
        countOfWords.text = properties.countOfWords
    }

    override fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showCategoryEntities(entities: List<CategoryEntity>) {
        setEntities(entities)
    }

    override fun showLesson(categoryEntity: CategoryEntity) {
        val bundle = Bundle()
        bundle.putString("category", categoryEntity.category)
        val fragmentManager = fragmentManager
        val fragment =
            LessonFragment()
        fragment.arguments = bundle
        fragmentManager!!.beginTransaction().replace(R.id.fragment_lesson, fragment)
            .addToBackStack(null).commit()
    }

    override fun showResources(resources: List<ResourcesOfCategory>) {
        setResources(resources)
    }
}