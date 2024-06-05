package com.example.instagram_clone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.R
import com.example.instagram_clone.databinding.FragmentAddBinding
import com.example.instagram_clone.databinding.FragmentProfileBinding
import com.example.instagram_clone.post.postActivity
import com.example.instagram_clone.post.reelActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.postadd.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), postActivity::class.java))
            activity?.finish()
        }
        binding.reeladd.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), reelActivity::class.java))
        }
        return binding.root
    }

    companion object {

    }
}