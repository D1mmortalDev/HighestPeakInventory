package com.example.productinventoryapp.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.productinventoryapp.dataClass.User
import com.example.productinventoryapp.dataClass.UserType
import com.example.productinventoryapp.database.UserTypes
import com.example.productinventoryapp.database.Users
import com.example.productinventoryapp.databinding.ActivityUserRegistrationBinding

class UserRegistration : AppCompatActivity() {
    private lateinit var binding: ActivityUserRegistrationBinding
    private lateinit var userDb : Users
    private lateinit var userTypeDb: UserTypes
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUserRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDb = Users(this)
        userTypeDb = UserTypes(this)

        binding.btnLogin2.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val fullName = binding.editTxtName.text.toString()
            val email = binding.editTxtEmail.text.toString()
            val mobileNum = binding.editTxtNumber.text.toString().toLong()
            val passwordFirst= binding.firstPass.text.toString()
            val passwordSecond = binding.secondPass.text.toString()

            if(fullName.isEmpty() || email.isEmpty() || passwordFirst.isEmpty() || passwordSecond.isEmpty() || mobileNum == null){
                Toast.makeText(applicationContext,"Fill the required fields",Toast.LENGTH_SHORT).show()
            }
            else{
                if(passwordFirst!=passwordSecond){
                    Toast.makeText(applicationContext,"Password does not match, try again",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    //Changes will be made on default user, Im still planning for better code.
                    val defaultUserType ="Employee"
                    //new user for User Table
                    val newUser = User(0,fullName,email, mobileNum)
                    //inserting data in User Table
                    registerUser(newUser)
                    //retrieving ID from inserted User data
                    val newUserId = getUserId(email)
                    //inserting new userType
                    val newUserType =UserType(0,email,passwordFirst,defaultUserType,newUserId)
                    insertUserType(newUserType)
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
    private fun registerUser(user:User) {
        userDb.insertUser(user)
        Toast.makeText(applicationContext,"Your account has been registered",Toast.LENGTH_SHORT).show()
    }
    private fun insertUserType(newUserType: UserType) {
        userTypeDb.insertUserType(newUserType)
        Toast.makeText(applicationContext,"Test for usertype- inserted successfully",Toast.LENGTH_SHORT).show()
    }
    private fun getUserId(username:String):Int{
        val id = userDb.getUserId(username)
        //Toast.makeText(applicationContext,"User id retrieve ${id}",Toast.LENGTH_SHORT).show()
        return id
    }
}