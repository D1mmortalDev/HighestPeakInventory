package com.example.productinventoryapp.ViewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.productinventoryapp.dataClass.Product
import com.example.productinventoryapp.databinding.ProductItemLayoutBinding
class ProductViewHolder(var binding: ProductItemLayoutBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product){
        binding.txtprodName.text = product.productName
        binding.txtprodDesc.text = product.productDescription
        binding.txtQuantity.text = product.quantity.toString()
        binding.txtPrice.text ="₱ ${product.price.toString()}"
    }
}