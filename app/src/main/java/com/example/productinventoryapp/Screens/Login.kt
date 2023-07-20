package com.example.productinventoryapp.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.productinventoryapp.database.Users
import com.example.productinventoryapp.databinding.ActivityLoginBinding
import java.lang.NumberFormatException

class Login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var userDb: Users
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDb = Users(this)
        binding.btnlogin.setOnClickListener {
            try {
                val username = binding.edLog.text.toString()
                val password = binding.edPassword.text.toString()
                checkIfAccountExist(username,password)
            } catch (e: NumberFormatException) {
                // handler
                Toast.makeText(applicationContext,"Fields should be filled",Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnregister.setOnClickListener{
            val intent = Intent(this, UserRegistration::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun checkIfAccountExist(username:String,password:String) {
        val userCredential = userDb.checkUserCredentials(username,password)
        if(!userCredential){
            Toast.makeText(applicationContext,"Username and password are incorrect. Please try again",Toast.LENGTH_SHORT).show()
        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}