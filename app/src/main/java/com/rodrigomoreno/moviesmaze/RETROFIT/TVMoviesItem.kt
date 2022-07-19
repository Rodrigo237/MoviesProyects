package com.rodrigomoreno.moviesmaze.RETROFIT

data class TVMoviesItem(
    val _links: Links,
    val airdate: String,
    val airstamp: String,
    val airtime: String,
    val id: Int,
    val image: Any,
    val name: String,
    val number: Int,
    val rating: Rating,
    val runtime: Int,
    val season: Int,
    val show: Show,
    val summary: String,
    val type: String,
    val url: String
)