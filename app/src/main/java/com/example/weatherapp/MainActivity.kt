package com.example.weatherapp


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchWeatherData("Mumbai")
        SearchCity()
    }

    private fun SearchCity() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    private fun fetchWeatherData(cityName: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)
        val response =
            retrofit.getWeatherData(cityName, "", "metric")
        response.enqueue(object : Callback<WeatherApp> {
            override fun onResponse(
                call: Call<WeatherApp?>,
                response: Response<WeatherApp?>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    val temperature = responseBody.main.temp.toString()
                    val humidity = responseBody.main.humidity.toString()
                    val windSpeed = responseBody.wind.speed
                    val sunrise = responseBody.sys.sunrise.toLong()
                    val sunset = responseBody.sys.sunset.toLong()
                    val seaLevel = responseBody.main.pressure
                    val condition = responseBody.weather.firstOrNull()?.main?:"unknown"
                    val maxTemp = responseBody.main.temp_max
                    val minTemp = responseBody.main.temp_min

                    binding.temp.text = "$temperature °C"
                    binding.weather.text = condition
                    binding.maxTemp.text = "Max Temp: $maxTemp °C"
                    binding.minTemp.text = "Min Temp: $minTemp °C"
                    binding.humidity.text = "$humidity %"
                    binding.windSpeed.text = "$windSpeed m/s"
                    binding.sunRise.text = "${time(sunrise)}"
                    binding.sunset.text  = "${time(sunset)}"
                    binding.sea.text = "$seaLevel hPa"
                    binding.condition.text = condition
                    binding.day.text = dayName(System.currentTimeMillis())
                    binding.date.text = date()
                    binding.cityName.text = "$cityName "
                    //Log.d("TAG", "onResponse: $temperature")

                    changeImageAccordingToWeatherCondition(condition)
                }
            }

            private fun changeImageAccordingToWeatherCondition(conditions :String) {
                when(conditions){
                    "Clear Sky","Sunny","Clear" ->{
                        binding.root.setBackgroundResource(R.drawable.sunny_background)
                        binding.lottieAnimationView.setAnimation(R.raw.sun)
                    }
                    "Party Clouds","Clouds","Overcast","Mist","Foggy" ->{
                        binding.root.setBackgroundResource(R.drawable.colud_background)
                        binding.lottieAnimationView.setAnimation(R.raw.cloud)
                    }
                    "Light Rains","Drizzle","Moderate Rain","Showers","Heavy Rain" ->{
                        binding.root.setBackgroundResource(R.drawable.rain_background)
                        binding.lottieAnimationView.setAnimation(R.raw.rain)
                    }
                    "Light Snow","Moderate Snow","Heavy Snow","Blizzard" ->{
                        binding.root.setBackgroundResource(R.drawable.colud_background)
                        binding.lottieAnimationView.setAnimation(R.raw.cloud)
                    }
                    "Haze" -> {
                        binding.root.setBackgroundResource(R.drawable.colud_background)
                        binding.lottieAnimationView.setAnimation(R.raw.cloud)
                    }
                    else -> {
                        binding.root.setBackgroundResource(R.drawable.sunny_background)
                        binding.lottieAnimationView.setAnimation(R.raw.sun)
                    }
                }

                binding.lottieAnimationView.playAnimation()
            }

            override fun onFailure(
                call: Call<WeatherApp?>,
                t: Throwable
            ) {

            }

        })

    }
    private fun date(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format((Date()))
    }
    private fun time(timeStamp : Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format((Date(timeStamp*1000)))
    }
    fun dayName(timeStamp : Long) : String{
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))
    }
}