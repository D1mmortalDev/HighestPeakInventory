package com.example.productinventoryapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productinventoryapp.Adapter.CategoryAdapter
import com.example.productinventoryapp.R
import com.example.productinventoryapp.dataClass.Category
import com.example.productinventoryapp.dataClass.Product
import com.example.productinventoryapp.database.ProductCategory
import com.example.productinventoryapp.database.Products
import com.example.productinventoryapp.databinding.DialogCategoryLayoutBinding
import com.example.productinventoryapp.databinding.DialogLayoutBinding
import com.example.productinventoryapp.databinding.FragmentCategoryBinding
class FragmentCategory : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var prodCategory: ProductCategory
    private lateinit var adapter:CategoryAdapter
    private lateinit var categoryList: MutableList<Category>
    private lateinit var products: Products
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(layoutInflater,container,false)
        //1) database
        prodCategory = ProductCategory(context)
        //2) recycle view
        recyclerView = binding.categoryRecycleView
        //3) layout
//        recyclerView.layoutManager= LinearLayoutManager(context)
        recyclerView.layoutManager = GridLayoutManager(context,2)
        //4) Get Data from DB
        categoryList = getAllData()
        //5) Combine with Adapter
        adapter = CategoryAdapter(categoryList)

        //6) Onclick adapter for deleting
        adapter.onDeleteClick={category ->
//            showDeleteDialog(product)
            delete(category.id)
            categoryList.remove(category)
            recyclerView.adapter?.notifyDataSetChanged()
        }
        //7) Onclick adapter for updating
        adapter.onUpdateClick={category ->
            showUpdateDialog(category)
        }
        //8) recycle view and adapter
        recyclerView.adapter =adapter

        //9) floating button
        binding.categoryFloatingBtn.setOnClickListener {
            showAddDialog()
        }
        return binding.root
    }



    private fun showUpdateDialog(category: Category) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Update Category Detail")
        val dialogLayout=layoutInflater.inflate(R.layout.dialog_category_layout,null)
        val dialogBinding= DialogCategoryLayoutBinding.bind(dialogLayout)
        alertDialogBuilder.setView(dialogLayout)

        dialogBinding.etCategoryName.setText(category.categoryName)
        alertDialogBuilder.setPositiveButton("OK"){dialog,_->
            val newCategoryName = dialogBinding.etCategoryName.text.toString()
            val newCategory =Category(category.id,newCategoryName)
            update(newCategory)
            //find the index of the viewholder in the recycle view
            val updateCategoryListPosition =categoryList.indexOfFirst { it.id==category.id}
            if(updateCategoryListPosition!=-1){
                categoryList[updateCategoryListPosition] = newCategory
                adapter.notifyItemChanged(updateCategoryListPosition)
            }
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("Cancel"){dialog,_->
            dialog.dismiss()
        }
        val alertDialog:AlertDialog=alertDialogBuilder.create()
        alertDialog.show()

    }

    //ADD
    private fun addCategory(category: Category) {
        prodCategory.insertProdCategory(category)
        Toast.makeText(context,"New category added ${category.id}", Toast.LENGTH_SHORT).show()

    }
    //UPDATE
    private  fun update(category: Category){
        prodCategory.updateProdCategory(category)
        getAllData()
        Toast.makeText(context,"category name has been updated!${category.id} ", Toast.LENGTH_SHORT).show()
    }
    //DELETE
    private fun delete(id:Int) {
        prodCategory.deleteCategory(id)
        Toast.makeText(context,"Category has been deleted!", Toast.LENGTH_SHORT).show()
    }

    private fun showAddDialog() {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Add New Category")

            val dialogCategoryLayout=layoutInflater.inflate(R.layout.dialog_category_layout,null)
            val dialogBinding= DialogCategoryLayoutBinding.bind(dialogCategoryLayout)
            alertDialogBuilder.setView(dialogCategoryLayout)

            alertDialogBuilder.setPositiveButton("OK"){dialog,_->
                val categoryName = dialogBinding.etCategoryName.text.toString()
                val newCategory= Category(0,categoryName)
                addCategory(newCategory)
                categoryList.add(newCategory)
                recyclerView.adapter?.notifyDataSetChanged()
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
            val alertDialog: AlertDialog =alertDialogBuilder.create()
            alertDialog.show()
        }

    private fun getAllData(): MutableList<Category> {
        val categories = prodCategory.getAllProdCategories()
        return categories
    }
}