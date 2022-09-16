package com.example.learning_english_kotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.learning_english_kotlin.model.Category
import com.example.learning_english_kotlin.R
import com.example.learning_english_kotlin.model.ThemeProperty
import java.util.ArrayList

class CategoryAdapter(var categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var properties: List<ThemeProperty> = ArrayList()

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        if (onItemClickListener != null) {
            this.onItemClickListener = onItemClickListener
        }
    }

    fun setThemePropertyList(properties: List<ThemeProperty>) {
        this.properties = properties
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cv: CardView = itemView.findViewById<View>(R.id.cardView) as CardView
        var categoryName: TextView = itemView.findViewById<View>(R.id.tvCategory) as TextView
        var categoryIcon: ImageView = itemView.findViewById<View>(R.id.ivCategory) as ImageView

    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_category, parent, false)
        return ViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = properties[position]
        val cardView = holder.cv
        holder.categoryName.text = categories[position].categoryName
        holder.categoryIcon.setImageResource(categories[position].categoryImageResourceId)
        cardView.setOnClickListener {
            onItemClickListener.onItemClickedCategory(property)
        }
    }
}