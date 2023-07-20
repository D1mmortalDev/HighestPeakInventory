package com.example.productinventoryapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productinventoryapp.ViewHolder.ProductViewHolder
import com.example.productinventoryapp.dataClass.Product
import com.example.productinventoryapp.databinding.ProductItemLayoutBinding

class ProductAdapter(var productList: MutableList<Product>):RecyclerView.Adapter<ProductViewHolder>() {

    var onDeleteClick:((Product)->Unit)?=null
    var onUpdateClick:((Product)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemLayoutBinding.inflate(inflater,parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
        holder.binding.apply {
            imgViewEdit.setOnClickListener {
                onUpdateClick?.invoke(productList[position])
            }
            imgViewDelete.setOnClickListener {
                onDeleteClick?.invoke(productList[position])
              }
        }
    }

}