package com.rodrigomoreno.moviesmaze

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.MovieNameViewHolder
import com.rodrigomoreno.moviesmaze.R
import com.rodrigomoreno.moviesmaze.RETROFIT.ShowNombre.MoviesResponseNameItem
import com.rodrigomoreno.moviesmaze.RETROFIT.ShowNombre.Show

class MoviesNameAdapter(private val movieName:List<Show>): RecyclerView.Adapter<MovieNameViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieNameViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return MovieNameViewHolder(layoutInflater.inflate(R.layout.item_name,parent,false))
    }

    override fun onBindViewHolder(holder: MovieNameViewHolder, position: Int) {
        val item: Show =  movieName[position]
        holder.bind(item.image.medium,item.name,item.network.name,item.schedule.time,item.schedule.days)
    }

    override fun getItemCount(): Int {
        return movieName.size
    }


}