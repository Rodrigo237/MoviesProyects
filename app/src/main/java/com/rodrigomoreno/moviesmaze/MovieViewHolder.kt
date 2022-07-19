package com.rodrigomoreno.moviesmaze

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemMovieBinding.bind(view)

    fun bind(image:String,name: String, network:String, airdate: String,airtime:String){
        Picasso.get().load(image).into(binding.imageViewProgram)
        binding.tvTitleProgramByName.text = name
        binding.textViewNetworkByName.text = network
        binding.textViewScheduleTime.text = airdate
        binding.textViewScheduledays.text = airtime

    }
}