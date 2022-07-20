package com.rodrigomoreno.moviesmaze

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.RETROFIT.TVMoviesItem

class MoviesNameAdapter(private val movieName:List<TVMoviesItem>): RecyclerView.Adapter<MovieNameViewHolder>(){
    //Se asigna el layout que se utilizara en cada elemento de RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieNameViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return MovieNameViewHolder(layoutInflater.inflate(R.layout.item_movie,parent,false))
    }
    //Se obtienen los elementos que se usaran o se necesitan mostrar
    override fun onBindViewHolder(holder: MovieNameViewHolder, position: Int) {
        val item: TVMoviesItem =  movieName[position]
        holder.bind(item.show.name,item.show.network.name,item.show.schedule.time,item.show.schedule.days)
    }
    //Se cuenta la cantidad de elementos de la lista
    override fun getItemCount(): Int {
        return movieName.size
    }


}