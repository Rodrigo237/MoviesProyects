package com.rodrigomoreno.moviesmaze

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.databinding.ItemCaracterBinding
import com.squareup.picasso.Picasso

class CaracterViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemCaracterBinding.bind(view)

    fun bind(image:String,name: String){
        Picasso.get().load(image).into(binding.imageViewCaracter)
        binding.textViewNameCaracter.text = name

    }
}