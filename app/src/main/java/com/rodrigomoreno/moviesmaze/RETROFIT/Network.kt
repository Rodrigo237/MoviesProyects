package com.rodrigomoreno.moviesmaze.RETROFIT

import com.rodrigomoreno.moviesmaze.RETROFIT.Country

data class Network(
    val country: Country,
    val id: Int,
    val name: String,
    val officialSite: String
)