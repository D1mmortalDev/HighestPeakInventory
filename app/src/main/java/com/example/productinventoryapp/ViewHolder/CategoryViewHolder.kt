package com.example.productinventoryapp.ViewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.productinventoryapp.dataClass.Category
import com.example.productinventoryapp.dataClass.Product
import com.example.productinventoryapp.databinding.CategoryItemLayoutBinding

class CategoryViewHolder (var binding:CategoryItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(category: Category){
        binding.txtCategory.text = category.categoryName
    }
}