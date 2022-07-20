package com.rodrigomoreno.moviesmaze

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigomoreno.moviesmaze.Cast.CastResponseItem
import com.rodrigomoreno.moviesmaze.Cast.Character
import com.rodrigomoreno.moviesmaze.Cast.Person
import com.rodrigomoreno.moviesmaze.RETROFIT.APIService
import com.rodrigomoreno.moviesmaze.RETROFIT.TVMoviesItem
import com.rodrigomoreno.moviesmaze.common.Constantes
import com.rodrigomoreno.moviesmaze.databinding.ActivityDetailsBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var adapterC: CaracterAdapter
    private val caracterInfo = mutableListOf<Character>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerViewC()
        val id = intent.getIntExtra("id",0)
        getProgram(id)
        getCaracteres(id)
    }

    private fun initRecyclerViewC() {
        adapterC = CaracterAdapter(caracterInfo)
        binding.rvCaracter.layoutManager = LinearLayoutManager(this)
        binding.rvCaracter.adapter = adapterC
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constantes.TV_MAZE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun getProgram(id:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<CastResponseItem> = getRetrofit().create(APIService::class.java)
                .getInfoProgram("shows/1") //estatico
            val programTV: CastResponseItem? = call.body()
            runOnUiThread() {
                if (call.isSuccessful) {
                    val programInfo: CastResponseItem? = programTV
                    if (programInfo != null) {
                        Picasso.get().load(programTV.image.medium).into(binding.imageViewInfoProgram)
                        binding.textViewNameProgram.text = programInfo.name
                        binding.textViewNetworkProgram.text = programInfo.network.name
                        binding.textViewRating.text = programInfo.rating.average.toString()
                        binding.buttonVisitarSitio.text = programInfo.officialSite.toString()
                        binding.tvSummary.text = programInfo.summary
                        binding.textViewgenre.text = programInfo.genres.toString()
                        binding.textViewTimeProgram.text = programInfo.schedule.time
                        binding.textViewDaysProgram.text = programInfo.schedule.days.toString()
                    }
                } else {
                    Toast.makeText(this@DetailsActivity, "Ha ocurrido un error", Toast.LENGTH_LONG)
                        .show()
                }
            }

        }
        }


    private fun getCaracteres(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<List<Character>> = getRetrofit().create(APIService::class.java)
                .getCharacter("shows/$id/cast") //estatico
            val caracterlist: List<Character>? = call.body()
            runOnUiThread() {
                if (call.isSuccessful) {
                    val listC: List<Character>? = caracterlist
                    if (listC != null) {
                        caracterInfo.addAll(listC)
                    }
                    adapterC.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@DetailsActivity, "Ha ocurrido un error", Toast.LENGTH_LONG)
                        .show()
                }
            }

        }
    }
}

