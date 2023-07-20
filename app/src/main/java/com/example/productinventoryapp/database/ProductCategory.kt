package com.example.productinventoryapp.database

import android.content.Context
import com.example.productinventoryapp.dataClass.Category

class ProductCategory(context: Context?) : DatabaseHelper(context) {
    //insert product category
    fun insertProdCategory(category: Category){
        val db= writableDatabase
        val sql ="INSERT INTO productCategory (categoryName) VALUES(?)"
        val args = arrayOf(category.categoryName)
        db.execSQL(sql,args)
    }
    //get all categories
    fun getAllProdCategories():MutableList<Category>{
        val db = readableDatabase
        val cursor =db.rawQuery("SELECT * from productCategory",null)
        val categories = mutableListOf<Category>()

        while (cursor.moveToNext()){
            val id = cursor.getInt(0)
            val categoryName = cursor.getString(1)
            var newCategory = Category(id, categoryName)
            categories.add(newCategory)
        }
        cursor.close()
        return categories
    }

    fun updateProdCategory(category: Category){
        val db= writableDatabase
        val updateQuery = "UPDATE productCategory SET categoryName ='${category.categoryName}' WHERE category_id=${category.id}"
        db.execSQL(updateQuery)
    }
    fun deleteCategory(id:Int){
        val db = writableDatabase
        val deleteQuery = "DELETE FROM productCategory WHERE category_id=${id}"
        db.execSQL(deleteQuery)
    }
}