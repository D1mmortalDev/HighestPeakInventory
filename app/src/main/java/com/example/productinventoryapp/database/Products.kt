package com.example.productinventoryapp.database

import android.content.Context
import com.example.productinventoryapp.dataClass.Product

class Products(context: Context?):DatabaseHelper(context) {

    fun insertProduct(products: Product){
        val db = writableDatabase
        val sql ="INSERT INTO product(productName, productDescription, productQuantity, productPrice,category_id)VALUES(?,?,?,?,?)"
        val args = arrayOf(products.productName,products.productDescription,products.quantity,products.price,products.productCategoryId)
        db.execSQL(sql,args)
    }

    fun getAllProducts():MutableList<Product>{
        val db = readableDatabase
        val cursor =db.rawQuery("SELECT * FROM product",null)
        val productList = mutableListOf<Product>()

        while (cursor.moveToNext()){
            val id = cursor.getInt(0)
            val productName = cursor.getString(1)
            val productDescription = cursor.getString(2)
            val quantity =cursor.getInt(3)
            val price = cursor.getDouble(4)
            val categoryId=cursor.getInt(5)
            val newProducts = Product(id,productName,productDescription,quantity,price,categoryId)
            productList.add(newProducts)
        }
        cursor.close()
        return productList
    }

    fun updateProduct(products: Product){
        val db= writableDatabase
        val updateQuery ="UPDATE product SET productName='${products.productName}',productDescription='${products.productDescription}',productQuantity='${products.quantity}',productPrice='${products.price}',category_id='${products.productCategoryId}' WHERE id = ${products.id}"
        db.execSQL(updateQuery)
    }

    fun deleteProduct(id:Int){
        val db= writableDatabase
        val deleteQuery ="DELETE FROM product WHERE id=${id}"
        db.execSQL(deleteQuery)
    }
    fun  countAllProductPerCategory(categoryId:Int):Int{
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM category where category_id=${categoryId}",null)
        if(cursor!=null && cursor.count !=0)
            cursor.moveToNext()
        return cursor.count
    }

    fun getAllProductsPerCategory(id:Int):MutableList<Product>{
        val db = readableDatabase
        val cursor =db.rawQuery("SELECT * FROM product where category_id=${id}",null)
        val productList = mutableListOf<Product>()

        while (cursor.moveToNext()){
            val id = cursor.getInt(0)
            val productName = cursor.getString(1)
            val productDescription = cursor.getString(2)
            val quantity =cursor.getInt(3)
            val price = cursor.getDouble(4)
            val categoryId=cursor.getInt(5)
            val newProducts = Product(id,productName,productDescription,quantity,price,categoryId)
            productList.add(newProducts)
        }
        cursor.close()
        return productList
    }
}