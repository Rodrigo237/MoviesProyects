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

    //Declaración de variables
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var adapterC: CaracterAdapter
    private val caracterInfo = mutableListOf<Character>()

    //Metodo onCreate aqui se mandan a llamar los metodos utilizados durante esta clase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inicialización de ViewBinding
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerViewC() //Llamado del RecyclerView para obtener los actores del programa
        val id = intent.getIntExtra("id",0) //Se recibe el id para poder obtener la información de ese programa
        getProgram(id) //Metodo que obtiene los datos del programa
        getCaracteres(id) //Metodo que obtiene los actores del programa
    }
    //Metodo para montar el adapter del RV de los actores
    private fun initRecyclerViewC() {
        adapterC = CaracterAdapter(caracterInfo)
        binding.rvCaracter.layoutManager = LinearLayoutManager(this)
        binding.rvCaracter.adapter = adapterC
    }
    //Aqui se realiza la conexion de retrofit
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constantes.TV_MAZE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Se busca por id la información del programa, aunque en esta parte tuve que hacerlo estatico,
    //debido a que no recibe el "id" como deberia entonces se hizo estatico para probar que mostrara los elementos de algun programa
    private fun getProgram(id:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            //Se realiza una corotina para el proceso
            val call: Response<CastResponseItem> = getRetrofit().create(APIService::class.java)
                .getInfoProgram("shows/1") //estatico //Se recibe la respuesta del servicio
            val programTV: CastResponseItem? = call.body()
            runOnUiThread() {
                if (call.isSuccessful) {//Se muestra si la respuesta es positivo
                    val programInfo: CastResponseItem? = programTV//Se recibe lo que se consumio
                    if (programInfo != null) {
                        //Se hace cada una de las asignaciones de los elementos recibidos con el correspondiente en el layout utilizado
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
                    Toast.makeText(this@DetailsActivity, "Ha ocurrido un error", Toast.LENGTH_LONG)//muestra un mensaje si no se pudo lograr el consumo de rescursos
                        .show()
                }
            }

        }
        }

    //Se busca por id los actores del programa y en este pasa el mismo problema de que no se logra obtener el id eficientemente
    private fun getCaracteres(id: Int) {
        //Se realiza una corotina para el proceso
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<List<Character>> = getRetrofit().create(APIService::class.java)
                .getCharacter("shows/$id/cast") //Se recibe la respuesta del servicio
            val caracterlist: List<Character>? = call.body()
            runOnUiThread() {
                if (call.isSuccessful) {
                    val listC: List<Character>? = caracterlist//Se recibe lo que se consumio
                    if (listC != null) {//Se verifica que no es nulo lo que se recibe
                        caracterInfo.addAll(listC)//Se agrega en la lista de personajes
                    }
                    adapterC.notifyDataSetChanged() //Se notifica el cambio del adapter
                } else {
                    Toast.makeText(this@DetailsActivity, "Ha ocurrido un error", Toast.LENGTH_LONG)//muestra un mensaje si no se pudo lograr el consumo de rescursos
                        .show()
                }
            }

        }
    }
}

