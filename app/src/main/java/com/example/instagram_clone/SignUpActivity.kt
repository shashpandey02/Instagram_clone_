package com.example.instagram_clone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram_clone.Models.User
import com.example.instagram_clone.databinding.ActivitySignUpBinding

import com.example.instagram_clone.utils.USER_NODE
import com.example.instagram_clone.utils.USER_PROFILE_FOLDER
import com.example.instagram_clone.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.net.CookieHandler

class SignUpActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it != null) {
                    user.image = it
                    binding.profileImage.setImageURI(uri)

                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val text =
            "<font color=#FF000000>Already Have An Account</font> <font color=#1E88E5>Login?</font>"
        binding.login.setText(Html.fromHtml(text))
        user = User()

        binding.btnRegister.setOnClickListener {
            if (binding.Name.editText?.text.toString().equals("") or
                binding.Email.editText?.text.toString().equals("") or
                binding.Password.editText?.text.toString().equals("")
            ) {
                Toast.makeText(
                    this@SignUpActivity,
                    "Please Fill The Required Fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.Email.editText?.text.toString(),
                    binding.Password.editText?.text.toString()
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        user.name = binding.Name.editText?.text.toString()
                        user.email = binding.Email.editText?.text.toString()
                        user.password = binding.Password.editText?.text.toString()
                        Firebase.firestore.collection(USER_NODE)
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                                finish()
                            }
                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            result.exception?.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

            }

        }
        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.login.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }

    }

}