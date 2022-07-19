package com.rodrigomoreno.moviesmaze.Cast

data class Character(
    val _links: Links,
    val id: Int,
    val image: Image,
    val name: String,
    val url: String
)