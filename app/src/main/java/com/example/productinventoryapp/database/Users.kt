package com.example.productinventoryapp.database

import android.content.Context
import android.widget.Toast
import com.example.productinventoryapp.dataClass.Category
import com.example.productinventoryapp.dataClass.Product
import com.example.productinventoryapp.dataClass.User

class Users(context: Context):DatabaseHelper(context){
    fun insertUser(user: User){
        val db = writableDatabase
        val sql ="INSERT INTO user(fullName, email, mobileNumber)VALUES(?,?,?)"
        val args = arrayOf(user.fullName,user.email,user.mobileNumber)
        db.execSQL(sql,args)

    }
    fun  getUserId(username: String):Int{
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT user_id FROM user where email='${username}'",null)
        var userId:Int =-1
        if (cursor.moveToFirst()) {
            userId= cursor.getInt(0)
        }
        cursor.close()
        return userId
    }

    fun checkUserCredentials(username: String,password:String):Boolean{
        val db= readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user usr INNER JOIN userType usrT ON usr.user_id = usrT.user_id where email='${username}'and password='${password}'",null)
        if(cursor.count<=0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
       }

    }
