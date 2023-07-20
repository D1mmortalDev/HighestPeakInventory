package com.example.productinventoryapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DatabaseHelper(context: Context?):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){
    companion object{
        const val DATABASE_NAME ="highestPeakTest.db"
        const val DATABASE_VERSION=1


    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys=ON;")
        db.execSQL("""
            CREATE TABLE productCategory(
            category_id INTEGER PRIMARY KEY AUTOINCREMENT,
            categoryName TEXT
            )
        """.trimIndent())
        db.execSQL("""
            CREATE TABLE product(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            productName TEXT,
            productDescription TEXT,
            productQuantity INTEGER,
            productPrice DOUBLE,
            category_id INTEGER,
            FOREIGN KEY (category_id) REFERENCES productCategory(category_id) ON DELETE CASCADE)
        """.trimIndent())
        db.execSQL("""
            CREATE TABLE user(
            user_id INTEGER PRIMARY KEY AUTOINCREMENT,
            fullName TEXT,
            email TEXT,
            mobileNumber LONG
            )  
        """.trimIndent())
        db.execSQL("""
            CREATE TABLE userType(
            userType_id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT,
            password TEXT,
            userType TEXT,
            user_id INTEGER,
            FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE)
        """.trimIndent())

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS productCategory")
        db.execSQL("DROP TABLE IF EXISTS product")
        db.execSQL("DROP TABLE IF EXISTS user")
        db.execSQL("DROP TABLE IF EXISTS userType")
        onCreate(db)
    }
}