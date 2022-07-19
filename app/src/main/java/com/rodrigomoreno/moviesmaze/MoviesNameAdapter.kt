package com.rodrigomoreno.moviesmaze

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.RETROFIT.TVMoviesItem

class MoviesNameAdapter(private val movieName:List<TVMoviesItem>): RecyclerView.Adapter<MovieNameViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieNameViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return MovieNameViewHolder(layoutInflater.inflate(R.layout.item_name,parent,false))
    }

    override fun onBindViewHolder(holder: MovieNameViewHolder, position: Int) {
        val item: TVMoviesItem =  movieName[position]
        holder.bind(item.show.name,item.show.network.name,item.show.schedule.time,item.show.schedule.days)
    }

    override fun getItemCount(): Int {
        return movieName.size
    }


}