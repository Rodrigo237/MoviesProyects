package com.rodrigomoreno.moviesmaze

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.RETROFIT.Image
import com.rodrigomoreno.moviesmaze.databinding.ItemMovieBinding
import com.rodrigomoreno.moviesmaze.databinding.ItemNameBinding
import com.squareup.picasso.Picasso

class MovieNameViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemNameBinding.bind(view)

    fun bind(name: String, network:String, timeSchedule: String, airtime: List<String>){
        binding.tvTitleProgramByName.text = name
        binding.textViewNetworkByName.text = network
        binding.textViewScheduleTime.text = timeSchedule
        binding.textViewScheduledays.text = airtime.toString()

    }
}