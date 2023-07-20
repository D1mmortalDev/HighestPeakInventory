package com.example.productinventoryapp.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.productinventoryapp.Fragment.FragmentProductDetails
import com.example.productinventoryapp.Screens.ProductList
import com.example.productinventoryapp.ViewHolder.CategoryViewHolder
import com.example.productinventoryapp.dataClass.Category
import com.example.productinventoryapp.dataClass.Product
import com.example.productinventoryapp.databinding.CategoryItemLayoutBinding

class CategoryAdapter(var categoryList: MutableList<Category>):RecyclerView.Adapter<CategoryViewHolder>() {
    var onDeleteClick:((Category)->Unit)?=null
    var onUpdateClick:((Category)->Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemLayoutBinding.inflate(inflater,parent,false)
        return CategoryViewHolder(binding)
    }
    override fun getItemCount(): Int {
    return categoryList.size
    }
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
      holder.bind(categoryList[position])
        holder.itemView.setOnClickListener {
            var myIntent = Intent(holder.itemView.context, ProductList::class.java)
            myIntent.putExtra("cat_id", categoryList[position].id)
            holder.itemView.context.startActivity(myIntent)
        }
        holder.binding.apply {
            imgBtnRemove.setOnClickListener {
                onDeleteClick?.invoke(categoryList[position])
            }
            imgBtnUpdate.setOnClickListener {
                onUpdateClick?.invoke(categoryList[position])
            }
        }
    }
}