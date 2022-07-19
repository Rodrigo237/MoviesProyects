package com.rodrigomoreno.moviesmaze

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigomoreno.moviesmaze.RETROFIT.APIService
import com.rodrigomoreno.moviesmaze.RETROFIT.TVMoviesItem
import com.rodrigomoreno.moviesmaze.common.Constantes
import com.rodrigomoreno.moviesmaze.databinding.ActivityDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val programInfo = mutableListOf<TVMoviesItem>()
    private lateinit var adapter: ProgramInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        getInfoProgram(id)
        initRecyclerView()
    }
    private fun initRecyclerView() {
        adapter = ProgramInfoAdapter(programInfo)
        binding.rvProgramInfo.layoutManager = LinearLayoutManager(this)
        binding.rvProgramInfo.adapter = adapter
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constantes.TV_MAZE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun getInfoProgram(id: String?) {
            CoroutineScope(Dispatchers.IO).launch {
                val call: Response<List<TVMoviesItem>> = getRetrofit().create(APIService::class.java)
                    .getMovies("/shows/$id")
                val movieTV: List<TVMoviesItem>? = call.body()
                runOnUiThread() {
                    if (call.isSuccessful) {
                        val listTVMovies: List<TVMoviesItem>? = movieTV
                        if (listTVMovies != null) {
                            programInfo.addAll(listTVMovies)
                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@DetailsActivity, "Ha ocurrido un error", Toast.LENGTH_LONG)
                            .show()
                    }
                }

            }
        }
}