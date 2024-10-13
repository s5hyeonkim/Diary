package com.sohykim.drinktrip

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sohykim.drinktrip.databinding.ActivityLoginBinding
import com.sohykim.drinktrip.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    var auth : FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.emailLoginButton.setOnClickListener {
            signinAndSignup()
        }
    }
    fun signinAndSignup(){
        auth?.createUserWithEmailAndPassword(binding.emailEdittext.text.toString(), binding.passwordEdittext.text.toString())?.addOnCompleteListener {
            task ->
                if(task.isSuccessful){
                    moveMainPage(task.result.user)
                }else if (task.exception?.message.isNullOrEmpty()) {
                    // show the error message
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }else {
                    //login if you have account
                    signInEmail()
                }
        }
    }
    fun signInEmail(){
        auth?.createUserWithEmailAndPassword(binding.emailEdittext.text.toString(), binding.passwordEdittext.text.toString())?.addOnCompleteListener {
                task ->
            if(task.isSuccessful){
                moveMainPage(task.result.user)
                //login
            }
            else{
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                // show the error message
            }
        }
    }
    fun moveMainPage(user:FirebaseUser?){
        if (user != null){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}