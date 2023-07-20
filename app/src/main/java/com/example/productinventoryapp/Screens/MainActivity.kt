package com.example.productinventoryapp.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.productinventoryapp.Fragment.FragmentCategory
import com.example.productinventoryapp.Fragment.FragmentProductDetails
import com.example.productinventoryapp.R
import com.example.productinventoryapp.dataClass.Product
import com.example.productinventoryapp.databinding.AboutDialogLayoutBinding
import com.example.productinventoryapp.databinding.ActivityMainBinding
import com.example.productinventoryapp.databinding.DialogLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryList = FragmentCategory()
        val productList = FragmentProductDetails()

        // Set the initial fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView2, categoryList)
            commit()
        }

        binding.bottomNavigationView3.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ItemCategories -> {
                    this.supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainerView2, categoryList)
                        commit()
                    }

                }
                R.id.ItemAllProducts -> {
                    this.supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainerView2, productList)
                        commit()
                    }

                }
            }
            true
        }
        binding.mainAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.item_about->{
                    showDialog()
                }
            }
            true
        }

    }

    private fun showDialog() {
            val alertDialogBuilder = AlertDialog.Builder(this)
            //declare layout for showdialog
            val dialogLayout=layoutInflater.inflate(R.layout.about_dialog_layout,null)
            alertDialogBuilder.setView(dialogLayout)
            alertDialogBuilder.setNegativeButton("Close"){dialog,_->
                dialog.dismiss()
            }
            val alertDialog: AlertDialog =alertDialogBuilder.create()
            alertDialog.show()
        }
    }


