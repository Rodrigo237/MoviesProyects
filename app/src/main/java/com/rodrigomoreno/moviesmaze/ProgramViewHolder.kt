package com.rodrigomoreno.moviesmaze

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.databinding.ItemProgramBinding
import com.squareup.picasso.Picasso

class ProgramViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemProgramBinding.bind(view)

    fun bind(
        image: String,
        name: String, network: String, rating: Double,
        officialSite: String,
        summary: String,
        genre: String,
        time: String,
        daysSchedule:List<String>
    ){
        Picasso.get().load(image).into(binding.imageViewProgram)
        binding.tvTitleProgram.text = name
        binding.textViewNetwork.text = network
        binding.textViewTime.text = airdate
        binding.textViewScheduledays.text = airtime

    }
}