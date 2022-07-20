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

    //Declaración de variables
    private lateinit var binding: ActivityMainBinding
    val fechahoy: String = "2022-07-20"
    private lateinit var adapter: MoviesAdapter
    private lateinit var adapterName : MoviesNameAdapter
    private val moviesInfo = mutableListOf<TVMoviesItem>()
    private val moviesByName = mutableListOf<TVMoviesItem>()

    //Metodo onCreate aqui se mandan a llamar los metodos utilizados durante esta clase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inicialización de ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
            initRecyclerViewByName() //Llamado del RecyclerView paraa busqueda por Nombre
        binding.svMovies.setOnQueryTextListener(this) //Declaración de metodo onQuerytextListener
            initRecyclerView()//Llamado del RecyclerView paraa busqueda por fecha
            getToday() //Metodo que realiza la busqueda por fecha

        //Esta parte muestra de inicio el recycler View por fecha pero al dar click en el Search
        //desaperece ese RV para solo mostrar la busqueda por nombre
        binding.rvMoviesByName.isGone = true
        binding.svMovies.setOnSearchClickListener {
            binding.rvMovies.isGone = true
            binding.textViewFecha.isGone = true
            binding.rvMoviesByName.isGone = false
        }


    }

    //Metodo para montar el adapter del RV por nombre
    private fun initRecyclerViewByName() {
        adapterName = MoviesNameAdapter(moviesByName)
        binding.rvMoviesByName.layoutManager = LinearLayoutManager(this)
        binding.rvMoviesByName.adapter = adapterName
    }

    //Metodo para montar el adapter del RV por fecha
    private fun initRecyclerView() {
        adapter = MoviesAdapter(moviesInfo,this)
        binding.rvMovies.layoutManager = LinearLayoutManager(this)
        binding.rvMovies.adapter = adapter
    }

    //Aqui se realiza la conexion de retrofit
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constantes.TV_MAZE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Se busca por fecha de programa
    private fun getToday() {
        //Se realiza una corotina para el proceso
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<List<TVMoviesItem>> = getRetrofit().create(APIService::class.java)
                .getMovies("schedule?country=US&date=$fechahoy") //Se recibe la respuesta del servicio
            val movieTV: List<TVMoviesItem>? = call.body()
            runOnUiThread() {
                if (call.isSuccessful) {//Se muestra si la respuesta es positivo
                    val listTVMovies: List<TVMoviesItem>? = movieTV //Se recibe lo que se consumio
                    if (listTVMovies != null) { //Se verifica que no es nulo lo que se recibe
                        moviesInfo.addAll(listTVMovies) //Se agrega en la lista de movies
                    }
                    binding.textViewFecha.text = fechahoy //Se muestra la fecha que se seleccione
                    adapter.notifyDataSetChanged() //Se notifica el cambio del adapter
                } else {
                    Toast.makeText(this@MainActivity, "Ha ocurrido un error", Toast.LENGTH_LONG) //muestra un mensaje si no se pudo lograr el consumo de rescursos
                        .show()
                }
            }

        }
    }

    //Se busca por nombre de programa
    @SuppressLint("NotifyDataSetChanged")
    private fun searchByName(query: String) {
        //Se realiza una corotina para el proceso
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<List<TVMoviesItem>> = getRetrofit().create(APIService::class.java)
                .getMoviesByName("search/shows?q=$query")//Se recibe la respuesta del servicio
            val moviebyName: List<TVMoviesItem>? = call.body()
            runOnUiThread() {
                if (call.isSuccessful) {//Se muestra si la respuesta es positivo
                    val listByName: List<TVMoviesItem>? = moviebyName//Se recibe lo que se consumio
                    if (listByName != null) {//Se verifica que no es nulo lo que se recibe
                        moviesByName.addAll(listByName)//Se agrega en la lista de movies
                    }
                    adapterName.notifyDataSetChanged()//Se notifica el cambio del adapter
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
            searchByName(query) //Se llama el metodo de SearchView
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    //Este metodo entra cuando en la pantalla inicial al seleccionar un elemento se pasara a ver la descrpcion general del programa seleccionado
    override fun onMovieItemCliked(position: Int) {
        //Se implementa el Intent
        val intent = Intent(this,DetailsActivity::class.java)
        intent.putExtra("id",moviesInfo[position].id) //Se le pasa el id del elemento seleccionado
        startActivity(intent) //Se inicia la otra activity
    }


}