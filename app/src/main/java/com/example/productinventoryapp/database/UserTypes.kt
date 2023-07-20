package com.example.productinventoryapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.productinventoryapp.dataClass.Product
import com.example.productinventoryapp.dataClass.UserType

class UserTypes(context: Context):DatabaseHelper(context) {
    fun insertUserType(userType: UserType){
        val db = writableDatabase
        val sql ="INSERT INTO userType(username, password, userType,user_id)VALUES(?,?,?,?)"
        val args = arrayOf(userType.username,userType.password,userType.userType,userType.user_id)
        db.execSQL(sql,args)

    }
}