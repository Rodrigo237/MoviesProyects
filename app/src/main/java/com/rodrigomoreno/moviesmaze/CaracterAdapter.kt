package com.rodrigomoreno.moviesmaze

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.Cast.Character
import com.rodrigomoreno.moviesmaze.Cast.Person

class CaracterAdapter(private val caracter: List<Character>):RecyclerView.Adapter<CaracterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaracterViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return CaracterViewHolder(layoutInflater.inflate(R.layout.item_caracter,parent,false))
    }

    override fun onBindViewHolder(holder: CaracterViewHolder, position: Int) {
        val item: Character =  caracter[position]
        holder.bind(item.image.original,item.name)
    }

    override fun getItemCount(): Int {
        return caracter.size
    }

}