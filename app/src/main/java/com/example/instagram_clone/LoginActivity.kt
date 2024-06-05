package com.example.instagram_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.instagram_clone.Models.User
import com.example.instagram_clone.databinding.ActivityLoginBinding
import com.example.instagram_clone.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnCreateAcc.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            finish()
        }
        binding.btnLogin.setOnClickListener {
            if (binding.loginEmail.editText?.text.toString().equals("") or
                binding.loginPassword.editText?.text.toString().equals("")
            ) {
                Toast.makeText(this@LoginActivity, "Fill The Required Fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var user = User(
                    binding.loginEmail.editText?.text.toString(),
                    binding.loginPassword.editText?.text.toString()
                )
                Firebase.auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                it.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
            }
        }


    }
}