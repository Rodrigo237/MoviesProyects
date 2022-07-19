package com.rodrigomoreno.moviesmaze

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.RETROFIT.Image
import com.rodrigomoreno.moviesmaze.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieNameViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemMovieBinding.bind(view)

    fun bind(name: String, network:String, timeSchedule: String, airtime: List<String>){
        binding.tvTitleProgram.text = name
        binding.textViewNetwork.text = network
        binding.textViewTime.text = timeSchedule
        binding.textViewScheduledays.text = airtime.toString()

    }
}