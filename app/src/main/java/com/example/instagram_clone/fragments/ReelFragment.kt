package com.example.instagram_clone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.Models.Reel
import com.example.instagram_clone.R
import com.example.instagram_clone.adapters.ReelAdapter
import com.example.instagram_clone.databinding.FragmentReelBinding
import com.example.instagram_clone.databinding.MyPostRvDesignBinding
import com.example.instagram_clone.utils.REEL
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelFragment : Fragment() {

    private lateinit var binding: FragmentReelBinding
    lateinit var adapter:ReelAdapter
    var reelList = ArrayList<Reel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReelBinding.inflate(inflater, container, false)
        adapter = ReelAdapter(requireContext(), reelList)
        binding.viewPager.adapter = adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList = ArrayList<Reel>()
            reelList.clear()
            for(i in it.documents){
                var reel = i.toObject<Reel>()!!
                tempList.add(reel)

            }
            reelList.addAll(tempList)
            adapter.notifyDataSetChanged()

        }
        return binding.root
    }

    companion object {

    }
}