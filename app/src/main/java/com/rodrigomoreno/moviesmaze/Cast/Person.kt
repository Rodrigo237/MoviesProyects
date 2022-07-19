package com.rodrigomoreno.moviesmaze.Cast

data class Person(
    val _links: LinksX,
    val birthday: String,
    val country: Country,
    val deathday: Any,
    val gender: String,
    val id: Int,
    val image: Image,
    val name: String,
    val updated: Int,
    val url: String
)