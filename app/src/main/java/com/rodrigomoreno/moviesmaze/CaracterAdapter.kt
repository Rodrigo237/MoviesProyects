package com.rodrigomoreno.moviesmaze

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigomoreno.moviesmaze.Cast.Character
import com.rodrigomoreno.moviesmaze.Cast.Person

class CaracterAdapter(private val caracter: List<Character>):RecyclerView.Adapter<CaracterViewHolder>() {
    //Se asigna el layout que se utilizara en cada elemento de RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaracterViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return CaracterViewHolder(layoutInflater.inflate(R.layout.item_caracter,parent,false))
    }
    //Se obtienen los elementos que se usaran o se necesitan mostrar
    override fun onBindViewHolder(holder: CaracterViewHolder, position: Int) {
        val item: Character =  caracter[position]
        holder.bind(item.image.original,item.name)
    }
    //Se cuenta la cantidad de elementos de la lista
    override fun getItemCount(): Int {
        return caracter.size
    }

}