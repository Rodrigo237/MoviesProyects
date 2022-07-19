package com.rodrigomoreno.moviesmaze

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.RETROFIT.TVMoviesItem

class MoviesAdapter(private val movie:List<TVMoviesItem>):RecyclerView.Adapter<MovieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie,parent,false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item: TVMoviesItem =  movie[position]
        holder.bind(item.show.image.medium,item.name,item.show.network.name,item.airdate,item.airtime)
    }

    override fun getItemCount(): Int {
        return movie.size
    }
}