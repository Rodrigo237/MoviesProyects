package com.rodrigomoreno.moviesmaze

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigomoreno.moviesmaze.RETROFIT.APIService
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.rodrigomoreno.moviesmaze.RETROFIT.TVMoviesItem
import com.rodrigomoreno.moviesmaze.common.Constantes
import com.rodrigomoreno.moviesmaze.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnQueryTextListener,onMovieClickListener {

    private lateinit var binding: ActivityMainBinding
    val fechahoy: String = "2013-06-24"
    private lateinit var adapter: MoviesAdapter
    private lateinit var adapterName : MoviesNameAdapter
    private val moviesInfo = mutableListOf<TVMoviesItem>()
    private val moviesByName = mutableListOf<TVMoviesItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
            initRecyclerViewByName()
        binding.svMovies.setOnQueryTextListener(this)
            initRecyclerView()
            getToday()
        binding.rvMoviesByName.isGone = true
        binding.svMovies.setOnSearchClickListener {
            binding.rvMovies.isGone = true
            binding.textViewFecha.isGone = true
            binding.rvMoviesByName.isGone = false
        }


    }

    private fun initRecyclerViewByName() {
        adapterName = MoviesNameAdapter(moviesByName)
        binding.rvMoviesByName.layoutManager = LinearLayoutManager(this)
        binding.rvMoviesByName.adapter = adapterName
    }


    private fun initRecyclerView() {
        adapter = MoviesAdapter(moviesInfo,this)
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
            val call: Response<List<TVMoviesItem>> = getRetrofit().create(APIService::class.java)
                .getMoviesByName("search/shows?q=$query")
            val moviebyName: List<TVMoviesItem>? = call.body()
            runOnUiThread() {
                if (call.isSuccessful) {
                    val listByName: List<TVMoviesItem>? = moviebyName
                    if (listByName != null) {
                        moviesByName.addAll(listByName)
                    }
                    adapterName.notifyDataSetChanged()
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

    override fun onMovieItemCliked(position: Int) {
        val intent = Intent(this,DetailsActivity::class.java)
        intent.putExtra("id",moviesInfo[position].id)
        startActivity(intent)
    }


}