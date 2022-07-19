package com.rodrigomoreno.moviesmaze.Cast

data class CastResponseItem(
    val character: Character,
    val person: Person,
    val self: Boolean,
    val voice: Boolean
)