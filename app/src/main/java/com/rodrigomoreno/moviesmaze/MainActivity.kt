package com.rodrigomoreno.moviesmaze

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigomoreno.moviesmaze.RETROFIT.APIService
import com.rodrigomoreno.moviesmaze.RETROFIT.Schedule
import com.rodrigomoreno.moviesmaze.RETROFIT.TVMovies
import com.rodrigomoreno.moviesmaze.RETROFIT.TVMoviesItem
import com.rodrigomoreno.moviesmaze.common.Constantes
import com.rodrigomoreno.moviesmaze.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val fechahoy: String = "2022-07-18"
    private lateinit var adapter: MoviesAdapter
    private val moviesInfo = mutableListOf<TVMoviesItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getToday()
    }

    private fun initRecyclerView() {
        adapter = MoviesAdapter(moviesInfo)
        binding.rvMovies.layoutManager = LinearLayoutManager(this)
        binding.rvMovies.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constantes.TV_MAZE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun getToday() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<List<TVMoviesItem>> = getRetrofit().create(APIService::class.java)
                .getMovies("schedule?country=US&date=$fechahoy") //estatico
            val movieTV: List<TVMoviesItem>? = call.body()
            runOnUiThread() {
                if (call.isSuccessful) {
                    val listTVMovies: List<TVMoviesItem>? = movieTV
                    if (listTVMovies != null) {
                        moviesInfo.addAll(listTVMovies)
                    }
                    binding.textViewFecha.text = fechahoy
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Ha ocurrido un error", Toast.LENGTH_LONG)
                        .show()
                }
            }

        }
    }

    //Se busca por nombre de programa
    @SuppressLint("NotifyDataSetChanged")
    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<MoneyResponse> = getRetrofit().create(APIService::class.java)
                .getMoneyByUSD("country/getCurrency?currency=$query&dummy=true") //estatico
            // val call : Response<MoneyResponse> = getRetrofit().create(APIService::class.java).getMoneyByUSD("country/getCurrency?currency=$query") // servicio dinamico
            val usd: MoneyResponse? = call.body()
            runOnUiThread() {
                if (call.isSuccessful) {
                    val listUSD: List<Data>? = usd?.listMoney
                    if (listUSD != null) {
                        moneyInfo.addAll(listUSD)
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Ha ocurrido un error", Toast.LENGTH_LONG)
                        .show()
                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchByName(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }



}