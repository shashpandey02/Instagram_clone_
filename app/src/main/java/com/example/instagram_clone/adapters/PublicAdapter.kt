package com.example.instagram_clone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram_clone.Models.Post
import com.example.instagram_clone.Models.User
import com.example.instagram_clone.R
import com.example.instagram_clone.databinding.FragmentHomeBinding
import com.example.instagram_clone.databinding.PublicPostBinding
import com.example.instagram_clone.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import java.lang.Exception

class PublicAdapter(var context : Context, var postList: ArrayList<Post>) : RecyclerView.Adapter<PublicAdapter.MyHolder>(){
    inner class MyHolder(var binding: PublicPostBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
       var binding = PublicPostBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {

        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try{
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get().addOnSuccessListener {
                var user = it.toObject<User>()
                Glide.with(context).load(user!!.image).placeholder(R.drawable.user).into(holder.binding.publicprofile)
                holder.binding.publicname.text= user.name

            }
        }catch (e:Exception){}


        Glide.with(context).load(postList.get(position).postUrl).placeholder(R.drawable.reload).into(holder.binding.publicPost)
        holder.binding.time.text = postList.get(position).time
        holder.binding.captionPost.text = postList.get(position).caption

        holder.binding.like.setOnClickListener{
            holder.binding.like.setImageResource(R.drawable.favorite)
        }

    }
}