package com.example.climacast


import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.climacast.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.locks.Condition

// API Key:  ab8644b45096c094676662525e6247a9
class MainActivity : AppCompatActivity() {
    //Enablaing Binding
    private  val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
   // lateinit var autoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.statusBarColor=Color.TRANSPARENT

        fetchWeatherData("Solapur")
        //Search City function Call
        SearchCity()
        Toast.makeText(this,"Application Designed By Ashish",Toast.LENGTH_LONG).show()


    }

    private fun SearchCity() {
       val searchView=binding.searchView
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener,
            OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null) {
                    fetchWeatherData((query))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return true
            }

        } )
    }

    private fun fetchWeatherData(cityName:String) {
        val retrofit= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)

             val response= retrofit.getWeatherData(cityName,"ab8644b45096c094676662525e6247a9","metric")
        response.enqueue(object : Callback<WeatherApp>{
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody= response.body()
                if(response.isSuccessful && responseBody != null) {
                    val temperature=responseBody.main.temp.toString()
                    val humidity=responseBody.main.humidity
                    val windSpeed=responseBody.wind.speed
                    val sunRise=responseBody.sys.sunrise.toLong()
                    val sunSet=responseBody.sys.sunset.toLong()
                    val seaLevel=responseBody.main.pressure
                    val condition=responseBody.weather.firstOrNull()?.main?:"unknown"
                    val maxTemp=responseBody.main.temp_max
                    val minTemp=responseBody.main.temp_min


                    binding.temp.text="$temperature °C"
                    binding.weather.text=condition
                    binding.maxTemp.text="Max Temp: $maxTemp °C"
                    binding.minTemp.text="Min Temp: $minTemp °C"
                    binding.humidity.text="$humidity %"
                    binding.windSpeed.text="$windSpeed m/s"
                    binding.sunRise.text="${time(sunRise)}"
                    binding.sunSet.text="${time(sunSet)}"
                    binding.sea.text="$seaLevel hPa"
                    binding.condition.text=condition
                    binding.day.text=dayName(System.currentTimeMillis())
                        binding.date.text=date()
                        binding.cityName.text="$cityName"


                    //Log.d("TAG", "onResponse: $temperature")
                    //Function to change background images according to the weather conditions
                    changeImagesAccordingToWeatherCondition(condition)
                }
            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


    }

    private fun changeImagesAccordingToWeatherCondition(conditions:String) {
        when(conditions) {

            "Light Snow","Moderate Snow","Heavy Snow","Blizzard"->{
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView2.setAnimation(R.raw.snow)
            }

            "Clouds","Partly Clouds","Overcast","Mist","Foggy" ->{
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView2.setAnimation(R.raw.cloud)
            }

            "Clear Sky","Sunny","Clear"->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView2.setAnimation(R.raw.sun)
            }

            "Light Rain","Drizzle","Moderate Rain","Showers","Heavy Rain"->{
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView2.setAnimation(R.raw.rain)
            }

            else->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView2.setAnimation(R.raw.sun)
            }


        }
        binding.lottieAnimationView2.playAnimation()
    }

    private fun date(): String {
        val sdf= SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format((Date()))
    }

    private fun time(timestamp: Long): String {
        val sdf= SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format((Date(timestamp*1000)))
    }

    fun dayName(timestamp: Long): String{
        val sdf= SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))
    }
}