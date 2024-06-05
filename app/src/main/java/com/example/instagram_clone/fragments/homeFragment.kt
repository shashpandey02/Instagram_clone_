package com.example.instagram_clone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram_clone.Models.Post
import com.example.instagram_clone.R
import com.example.instagram_clone.adapters.PublicAdapter
import com.example.instagram_clone.databinding.FragmentHomeBinding
import com.example.instagram_clone.utils.POST
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class homeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var postList = ArrayList<Post>()
    private lateinit var adapter: PublicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater, container, false)
        adapter = PublicAdapter(requireContext(), postList)
        binding.recycleRv.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleRv.adapter = adapter
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.material2)

        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList = ArrayList<Post>()
            postList.clear()
            for(i in it.documents){
                var post = i.toObject<Post>()!!
                tempList.add(post)

            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }


        return binding.root
    }

    companion object {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}