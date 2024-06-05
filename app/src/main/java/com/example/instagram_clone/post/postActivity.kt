package com.example.instagram_clone.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram_clone.HomeActivity
import com.example.instagram_clone.Models.Post
import com.example.instagram_clone.Models.User
import com.example.instagram_clone.R
import com.example.instagram_clone.databinding.ActivityPostBinding
import com.example.instagram_clone.utils.POST
import com.example.instagram_clone.utils.USER_NODE
import com.example.instagram_clone.utils.USER_PROFILE_FOLDER
import com.example.instagram_clone.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class postActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER) { url ->
                if (url != null) {
                    binding.gallerypost.setImageURI(uri)
                    imageUrl = url;
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@postActivity, HomeActivity::class.java))
            finish()
        }
        binding.gallerypost.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.cancel.setOnClickListener {
            startActivity(Intent(this@postActivity, HomeActivity::class.java))
            finish()
        }
        binding.postdone.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document().get()
                .addOnSuccessListener {
                    var user = it.toObject<User>()!!
                    val post: Post = Post(
                        postUrl = imageUrl!!,
                        caption = binding.Caption.editText?.text.toString(),
                        uid = Firebase.auth.currentUser!!.uid,
                        time = System.currentTimeMillis().toString()
                    )

                    Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document()
                            .set(post).addOnSuccessListener {
                            startActivity(Intent(this@postActivity, HomeActivity::class.java))
                            finish()
                        }

                    }

                }


        }
    }
}