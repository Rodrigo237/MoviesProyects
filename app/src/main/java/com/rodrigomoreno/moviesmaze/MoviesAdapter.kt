package com.rodrigomoreno.moviesmaze

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.RETROFIT.TVMoviesItem
import com.rodrigomoreno.moviesmaze.databinding.ItemMovieBinding

class MoviesAdapter(private val movie:List<TVMoviesItem>, private val onMovieClickListener: MainActivity):RecyclerView.Adapter<MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie,parent,false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item: TVMoviesItem =  movie[position]
        holder.bind(item.show.image.medium,item.name,item.show.network.name,item.airdate,item.airtime)
        holder.itemView.setOnClickListener{
            onMovieClickListener.onMovieItemCliked(position)
        }
    }

    override fun getItemCount(): Int {
        return movie.size
    }

}