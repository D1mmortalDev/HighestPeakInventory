package com.example.productinventoryapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productinventoryapp.Adapter.ProductAdapter
import com.example.productinventoryapp.R
import com.example.productinventoryapp.dataClass.Product
import com.example.productinventoryapp.database.Products
import com.example.productinventoryapp.databinding.DialogLayoutBinding
import com.example.productinventoryapp.databinding.FragmentProductDetailsBinding
open class FragmentProductDetails : Fragment(){
    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var dbProduct: Products
    private lateinit var recyclerView: RecyclerView
    private lateinit var productList: MutableList<Product>
    private lateinit var adapter: ProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(layoutInflater,container,false)

        //0) Getting id from Category from another fragment (Fragment Category)
        val id = arguments?.getInt("id", 0) ?: 0

        //1) database
        dbProduct = Products(context)
        //2) recycle view
        recyclerView = binding.productRecycleView
        //3) layout
        recyclerView.layoutManager= LinearLayoutManager(context)
        //4) Get Data from DB
        productList = getAllData()
        //5) Combine with Adapter
        adapter = ProductAdapter(productList)

        //6) Onclick adapter for deleting
        adapter.onDeleteClick={product ->
            showDeleteDialog(product)
        }
        //7) Onclick adapter for updating
        adapter.onUpdateClick={product ->
            showUpdateDialog(product)
        }
        //8) recycle view and adapter
         recyclerView.adapter =adapter

        // 9) floating button
        binding.floatingActionButton.setOnClickListener {
            showAddDialog()
        }
        return binding.root
    }

    private fun showAddDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Add New Product")

        //declare layout for showdialog
        val dialogLayout=layoutInflater.inflate(R.layout.dialog_layout,null)
        val dialogBinding=DialogLayoutBinding.bind(dialogLayout)
        alertDialogBuilder.setView(dialogLayout)

//        dialogBinding.etDCategory.setText(id)
        alertDialogBuilder.setPositiveButton("OK"){dialog,_->
            val prodName=dialogBinding.etDProdName.text.toString()
            val prodDesc=dialogBinding.etDProdDesc.text.toString()
            val quantity= dialogBinding.etDQuantity.text.toString().toInt()
            val price =dialogBinding.etDPrice.text.toString().toDouble()
            val categoryId= dialogBinding.etDCategory.text.toString().toInt()
            val newProduct= Product(0,prodName,prodDesc,quantity,price,categoryId)
            addProduct(newProduct)
            productList.add(newProduct)
            recyclerView.adapter?.notifyDataSetChanged()
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("Cancel"){dialog,_->
            dialog.dismiss()
        }
        val alertDialog:AlertDialog=alertDialogBuilder.create()
        alertDialog.show()


    }
    private fun showUpdateDialog(product: Product) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Update Product Details")
        val dialogLayout=layoutInflater.inflate(R.layout.dialog_layout,null)
        val dialogBinding=DialogLayoutBinding.bind(dialogLayout)
        alertDialogBuilder.setView(dialogLayout)

        Toast.makeText(context,"${product.productCategoryId}",Toast.LENGTH_SHORT).show()

        dialogBinding.etDProdName.setText(product.productName)
        dialogBinding.etDProdDesc.setText(product.productDescription)
        dialogBinding.etDQuantity.setText(product.quantity.toString())
        dialogBinding.etDPrice.setText(product.price.toString())
        dialogBinding.etDCategory.setText(product.productCategoryId.toString())

        alertDialogBuilder.setPositiveButton("OK"){dialog,_->
            val prodName=dialogBinding.etDProdName.text.toString()
            val prodDesc=dialogBinding.etDProdDesc.text.toString()
            val quantity= dialogBinding.etDQuantity.text.toString().toInt()
            val price =dialogBinding.etDPrice.text.toString().toDouble()
            val categoryId= dialogBinding.etDCategory.text.toString().toInt()
            val newProduct=Product(product.id,prodName,prodDesc,quantity,price,categoryId)
            update(newProduct)
            //find the index of the viewholder in the recycle view
            val updateProductPosition =productList.indexOfFirst { it.id==product.id}
            if(updateProductPosition!=-1){
                productList[updateProductPosition] = newProduct
                adapter.notifyItemChanged(updateProductPosition)
            }
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("Cancel"){dialog,_->
            dialog.dismiss()
        }
        val alertDialog:AlertDialog=alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun showDeleteDialog(product: Product) {
        Toast.makeText(context, "Id ${product.id}", Toast.LENGTH_SHORT).show()
        val alertDialogBuilder= AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Remove Products")
        alertDialogBuilder.setMessage("Do you want to remove this product?")

        alertDialogBuilder.setPositiveButton("OK"){dialog,_->
            delete(product.id)
            productList.remove(product)
            recyclerView.adapter?.notifyDataSetChanged()
            dialog.dismiss()

        }
        alertDialogBuilder.setNegativeButton("Cancel"){dialog,_->
            dialog.dismiss()
        }
        val alertDialog:AlertDialog=alertDialogBuilder.create()
        alertDialog.show()

    }
    private fun getAllData(): MutableList<Product> {
        val products = dbProduct.getAllProducts()
        return  products
    }

    //ADD
    private fun addProduct(newProduct: Product) {
        dbProduct.insertProduct(newProduct)
        getAllData()
        Toast.makeText(context,"New product added ${newProduct.id}", Toast.LENGTH_SHORT).show()

    }
    //UPDATE
    private  fun update(product: Product){
        dbProduct.updateProduct(product)
        getAllData()
        Toast.makeText(context,"product details has been updated! ${product.id} ", Toast.LENGTH_SHORT).show()
    }
    //DELETE
    private fun delete(id:Int) {
        dbProduct.deleteProduct(id)
        getAllData()
        Toast.makeText(context,"Product has been deleted!", Toast.LENGTH_SHORT).show()
    }

}
