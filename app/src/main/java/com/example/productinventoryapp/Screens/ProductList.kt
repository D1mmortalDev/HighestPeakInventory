package com.example.productinventoryapp.Screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productinventoryapp.Adapter.ProductAdapter
import com.example.productinventoryapp.R
import com.example.productinventoryapp.dataClass.Product
import com.example.productinventoryapp.database.Products
import com.example.productinventoryapp.databinding.ActivityProductListBinding
import com.example.productinventoryapp.databinding.DialogLayoutBinding

class ProductList : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var dbProduct: Products
    private lateinit var recyclerView:RecyclerView
    private lateinit var productList:MutableList<Product>
    private lateinit var adapter:ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //1) database
        dbProduct = Products(this)
        //2) recycle view
        recyclerView = binding.recycleProductList
        //3) layout
        recyclerView.layoutManager = LinearLayoutManager(this)
        //get data from previous activity
        val category_id= intent.getIntExtra("cat_id",0)
        //4) Get Data from DB
        productList = getAllData(category_id)
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
        binding.floatingActionButton2.setOnClickListener {
            showAddDialog(category_id)
        }
        //8) recycle view and adapter
        recyclerView.adapter =adapter

    }

    private fun showAddDialog(category_id: Int) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Add New Product")

        //declare layout for showdialog
        val dialogLayout=layoutInflater.inflate(R.layout.dialog_layout,null)
        val dialogBinding=DialogLayoutBinding.bind(dialogLayout)
        alertDialogBuilder.setView(dialogLayout)

        dialogBinding.etDCategory.setText(category_id.toString())
        alertDialogBuilder.setPositiveButton("OK"){dialog,_->
            val prodName=dialogBinding.etDProdName.text.toString()
            val prodDesc=dialogBinding.etDProdDesc.text.toString()
            val quantity= dialogBinding.etDQuantity.text.toString().toInt()
            val price =dialogBinding.etDPrice.text.toString().toDouble()
            val categoryId= dialogBinding.etDCategory.text.toString().toInt()
            val newProduct= Product(0,prodName,prodDesc,quantity,price,categoryId)
            addProduct(newProduct,categoryId)
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
    private fun showDeleteDialog(product: Product) {
        val alertDialogBuilder= AlertDialog.Builder(this)
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
        val alertDialog: AlertDialog =alertDialogBuilder.create()
        alertDialog.show()

    }
    private fun showUpdateDialog(product: Product) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Update Product Details")
        val dialogLayout=layoutInflater.inflate(R.layout.dialog_layout,null)
        val dialogBinding= DialogLayoutBinding.bind(dialogLayout)
        alertDialogBuilder.setView(dialogLayout)

        Toast.makeText(this,"${product.productCategoryId}",Toast.LENGTH_SHORT).show()

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

    private fun getAllData(cat_id: Int): MutableList<Product> {
        val products = dbProduct.getAllProductsPerCategory(cat_id)
        return  products
    }

    //ADD
    private fun addProduct(newProduct: Product,id:Int) {
        dbProduct.insertProduct(newProduct)
        getAllData(id)
        Toast.makeText(this,"New product added ${newProduct.id}", Toast.LENGTH_SHORT).show()

    }
    //UPDATE
    private  fun update(product: Product){
        dbProduct.updateProduct(product)
        Toast.makeText(this,"product details has been updated!${product} ", Toast.LENGTH_SHORT).show()
    }
    //DELETE
    private fun delete(id:Int) {
        dbProduct.deleteProduct(id)
        Toast.makeText(this,"Product has been deleted!", Toast.LENGTH_SHORT).show()
    }


}