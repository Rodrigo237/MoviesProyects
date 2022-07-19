package com.rodrigomoreno.moviesmaze

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.RETROFIT.TVMoviesItem

class ProgramInfoAdapter(private val programInfo:List<TVMoviesItem>): RecyclerView.Adapter<ProgramViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ProgramViewHolder(layoutInflater.inflate(R.layout.item_program,parent,false))
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val item: TVMoviesItem =  programInfo[position]
        holder.bind(item.show.image.medium,item.show.name,item.show.network.name, item.show.rating.average,item.show.officialSite,
          item.show.summary,item.show.genres.toString(),item.show.schedule.time,item.show.schedule.days)
    }
    override fun getItemCount(): Int {
        return programInfo.size
    }


}