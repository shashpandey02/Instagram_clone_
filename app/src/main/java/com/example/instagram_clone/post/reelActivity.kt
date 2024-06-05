package com.example.instagram_clone.post

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram_clone.HomeActivity
import com.example.instagram_clone.Models.Post
import com.example.instagram_clone.Models.Reel
import com.example.instagram_clone.R
import com.example.instagram_clone.databinding.ActivityReelBinding
import com.example.instagram_clone.utils.POST
import com.example.instagram_clone.utils.REEL
import com.example.instagram_clone.utils.REEL_FOLDER
import com.example.instagram_clone.utils.USER_PROFILE_FOLDER
import com.example.instagram_clone.utils.uploadImage
import com.example.instagram_clone.utils.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class reelActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityReelBinding.inflate(layoutInflater)
    }
    private lateinit var videoUrl: String
    lateinit var progressDialog:ProgressDialog

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER, progressDialog) {
                    url ->
                    if (url != null) {
                        videoUrl = url;
                    }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        binding.materialToolbar.setOnClickListener {
            startActivity(Intent(this@reelActivity, HomeActivity::class.java))
            finish()
        }

        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }
        binding.cancel.setOnClickListener {
            startActivity(Intent(this@reelActivity, HomeActivity::class.java))
            finish()
        }
        binding.postreel.setOnClickListener {
            var reel: Reel = Reel(videoUrl!!, binding.Caption.editText?.text.toString())

            Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                startActivity(Intent(this@reelActivity, HomeActivity::class.java))
                finish()
            }
        }
    }
}