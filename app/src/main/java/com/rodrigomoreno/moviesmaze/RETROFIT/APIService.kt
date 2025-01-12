package com.rodrigomoreno.moviesmaze.RETROFIT

import com.rodrigomoreno.moviesmaze.Cast.CastResponseItem
import com.rodrigomoreno.moviesmaze.Cast.Character
import com.rodrigomoreno.moviesmaze.Cast.Person
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    //Se declaran las respuestas que se utilizaran para consumir los dintintos servicios que se utilizaran
    @GET
    suspend fun getMovies(@Url url:String):Response<List<TVMoviesItem>>

    @GET
    suspend fun getMoviesByName(@Url url:String):Response<List<TVMoviesItem>>

    @GET
    suspend fun getInfoProgram(@Url url: String):Response<CastResponseItem>

    @GET
    suspend fun getCharacter(@Url url: String):Response<List<Character>>

}