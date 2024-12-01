//package com.example.weatherapp
//
//
//import android.os.Bundle
//import android.widget.ArrayAdapter
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.widget.SearchView
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.example.weatherapp.databinding.ActivityMainBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//
//class MainActivity : AppCompatActivity() {
//    private val binding: ActivityMainBinding by lazy {
//        ActivityMainBinding.inflate(layoutInflater)
//    }
//    private val citySuggestions = listOf("Mumbai", "Delhi", "Bangalore", "Kolkata", "Chennai", "Hyderabad", "Pune")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//        fetchWeatherData("Mumbai")
//        setUpSearchSuggestions()
//    }
//
////    private fun SearchCity() {
////        val searchView = binding.searchView
////        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
////            override fun onQueryTextSubmit(query: String?): Boolean {
////                if (query != null) {
////                    fetchWeatherData(query)
////                }
////                return true
////            }
////
////            override fun onQueryTextChange(newText: String?): Boolean {
////                return true
////            }
////
////        })
////
////    }
//private fun setUpSearchSuggestions() {
//    val searchView = binding.searchView
//    val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, citySuggestions)
//    searchView.suggestionsAdapter = adapter
//
//    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//        override fun onQueryTextSubmit(query: String?): Boolean {
//            query?.let {
//                fetchWeatherData(it)
//                Toast.makeText(this@MainActivity, "Searching for: $it", Toast.LENGTH_SHORT).show()
//            }
//            return true
//        }
//
//        override fun onQueryTextChange(newText: String?): Boolean {
//            return true
//        }
//    })
//}
//
//    private fun fetchWeatherData(cityName: String) {
//        val retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://api.openweathermap.org/data/2.5/")
//            .build().create(ApiInterface::class.java)
//        val response =
//            retrofit.getWeatherData(cityName, "e39cd138c15bf6064a5ff70b74966b86", "metric")
//        response.enqueue(object : Callback<WeatherApp> {
//            override fun onResponse(
//                call: Call<WeatherApp?>,
//                response: Response<WeatherApp?>
//            ) {
//                val responseBody = response.body()
//                if(response.isSuccessful && responseBody != null){
//                    val temperature = responseBody.main.temp.toString()
//                    val humidity = responseBody.main.humidity.toString()
//                    val windSpeed = responseBody.wind.speed
//                    val sunrise = responseBody.sys.sunrise.toLong()
//                    val sunset = responseBody.sys.sunset.toLong()
//                    val seaLevel = responseBody.main.pressure
//                    val condition = responseBody.weather.firstOrNull()?.main?:"unknown"
//                    val maxTemp = responseBody.main.temp_max
//                    val minTemp = responseBody.main.temp_min
//
//                    binding.temp.text = "$temperature °C"
//                    binding.weather.text = condition
//                    binding.maxTemp.text = "Max Temp: $maxTemp °C"
//                    binding.minTemp.text = "Min Temp: $minTemp °C"
//                    binding.humidity.text = "$humidity %"
//                    binding.windSpeed.text = "$windSpeed m/s"
//                    binding.sunRise.text = "${time(sunrise)}"
//                    binding.sunset.text  = "${time(sunset)}"
//                    binding.sea.text = "$seaLevel hPa"
//                    binding.condition.text = condition
//                    binding.day.text = dayName(System.currentTimeMillis())
//                    binding.date.text = date()
//                    binding.cityName.text = "$cityName "
//                    //Log.d("TAG", "onResponse: $temperature")
//
//                    changeImageAccordingToWeatherCondition(condition)
//                }
//            }
//
//            private fun changeImageAccordingToWeatherCondition(conditions :String) {
//                when(conditions){
//                    "Clear Sky","Sunny","Clear" ->{
//                        binding.root.setBackgroundResource(R.drawable.sunny_background)
//                        binding.lottieAnimationView.setAnimation(R.raw.sun)
//                    }
//                    "Party Clouds","Clouds","Overcast","Mist","Foggy" ->{
//                        binding.root.setBackgroundResource(R.drawable.colud_background)
//                        binding.lottieAnimationView.setAnimation(R.raw.cloud)
//                    }
//                    "Light Rains","Drizzle","Moderate Rain","Showers","Heavy Rain" ->{
//                        binding.root.setBackgroundResource(R.drawable.rain_background)
//                        binding.lottieAnimationView.setAnimation(R.raw.rain)
//                    }
//                    "Light Snow","Moderate Snow","Heavy Snow","Blizzard" ->{
//                        binding.root.setBackgroundResource(R.drawable.colud_background)
//                        binding.lottieAnimationView.setAnimation(R.raw.cloud)
//                    }
//                    "Haze" -> {
//                        binding.root.setBackgroundResource(R.drawable.colud_background)
//                        binding.lottieAnimationView.setAnimation(R.raw.cloud)
//                    }
//                    else -> {
//                        binding.root.setBackgroundResource(R.drawable.sunny_background)
//                        binding.lottieAnimationView.setAnimation(R.raw.sun)
//                    }
//                }
//
//                binding.lottieAnimationView.playAnimation()
//            }
//
//            override fun onFailure(
//                call: Call<WeatherApp?>,
//                t: Throwable
//            ) {
//
//            }
//
//        })
//
//    }
//    private fun date(): String {
//        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
//        return sdf.format((Date()))
//    }
//    private fun time(timeStamp : Long): String {
//        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
//        return sdf.format((Date(timeStamp*1000)))
//    }
//    fun dayName(timeStamp : Long) : String{
//        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
//        return sdf.format((Date()))
//    }
//}
package com.example.weatherapp

import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.cursoradapter.widget.SimpleCursorAdapter
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

    private val citySuggestions = listOf(
        // Major Indian Cities
        "Agra", "Ahmedabad", "Ajmer", "Aligarh", "Allahabad", "Alwar", "Amritsar", "Aurangabad",
        "Bangalore", "Bareilly", "Belgaum", "Bhopal", "Bhubaneswar", "Bikaner", "Bilaspur",
        "Chandigarh", "Chennai", "Coimbatore", "Cuttack", "Dehradun", "Delhi", "Dhanbad",
        "Dibrugarh", "Durg", "Durgapur", "Faridabad", "Firozabad", "Gangtok", "Gandhinagar",
        "Gaya", "Ghaziabad", "Gorakhpur", "Guntur", "Gurgaon", "Guwahati", "Gwalior", "Hubli",
        "Hyderabad", "Imphal", "Indore", "Jabalpur", "Jaipur", "Jalandhar", "Jammu", "Jamshedpur",
        "Jhansi", "Jodhpur", "Kanpur", "Karur", "Kochi", "Kolhapur", "Kolkata", "Kozhikode",
        "Lucknow", "Ludhiana", "Madurai", "Mangalore", "Mathura", "Meerut", "Moradabad", "Mumbai",
        "Mysore", "Nagpur", "Nanded", "Nashik", "Noida", "Panaji", "Patiala", "Patna", "Pondicherry",
        "Pune", "Raipur", "Rajkot", "Ranchi", "Rourkela", "Salem", "Shillong", "Shimla", "Siliguri",
        "Solapur", "Srinagar", "Surat", "Thane", "Tiruchirappalli", "Tirupati", "Trivandrum",
        "Udaipur", "Ujjain", "Vadodara", "Varanasi", "Vellore", "Vijayawada", "Visakhapatnam", "Warangal",

        // Newly Added Indian Cities
        "Aizawl", "Alappuzha", "Ambala", "Bathinda", "Bokaro", "Chandrapur", "Dharamsala",
        "Dharmapuri", "Haldwani", "Hosur", "Itanagar", "Karnal", "Kollam", "Kumbakonam",
        "Latur", "Palakkad", "Pudukkottai", "Ranipet", "Roorkee", "Saharanpur", "Sonipat", "Tonk",

        // Global Cities
        "Abu Dhabi", "Accra", "Algiers", "Amsterdam", "Athens", "Atlanta", "Baghdad", "Bangkok",
        "Barcelona", "Beijing", "Berlin", "Brisbane", "Brussels", "Buenos Aires", "Cairo",
        "Cape Town", "Casablanca", "Chicago", "Colombo", "Copenhagen", "Dallas", "Damascus",
        "Doha", "Dubai", "Hanoi", "Helsinki", "Hong Kong", "Istanbul", "Jakarta", "Jerusalem",
        "Kabul", "Karachi", "Kathmandu", "Kiev", "Kuala Lumpur", "Kyoto", "Lagos", "Lahore",
        "Las Vegas", "Lisbon", "London", "Los Angeles", "Madrid", "Male", "Manila", "Melbourne",
        "Mexico City", "Miami", "Minsk", "Montreal", "Moscow", "Mumbai", "Nairobi", "New Delhi",
        "New York", "Osaka", "Oslo", "Paris", "Perth", "Phoenix", "Prague", "Rio de Janeiro",
        "Rome", "San Diego", "San Francisco", "Sao Paulo", "Seattle", "Seoul", "Shanghai",
        "Singapore", "Stockholm", "Sydney", "Taipei", "Tehran", "Tokyo", "Toronto", "Vancouver",
        "Vienna", "Warsaw", "Wellington", "Zurich"
    )


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
        setUpSearchSuggestions()
    }

    private fun setUpSearchSuggestions() {
        val searchView = binding.searchView

        // Create a SimpleCursorAdapter
        val cursorAdapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_1,
            null,
            arrayOf("city"),
            intArrayOf(android.R.id.text1),
            0
        )

        searchView.suggestionsAdapter = cursorAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    fetchWeatherData(it)
                    Toast.makeText(this@MainActivity, "Searching for: $it", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    updateSuggestions(cursorAdapter, newText)
                }
                return true
            }
        })

        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = cursorAdapter.cursor
                cursor.moveToPosition(position)
                val cityName = cursor.getString(cursor.getColumnIndexOrThrow("city"))
                searchView.setQuery(cityName, true)
                return true
            }
        })
    }

    private fun updateSuggestions(adapter: SimpleCursorAdapter, query: String) {
        val cursor = MatrixCursor(arrayOf(BaseColumns._ID, "city"))
        citySuggestions.filter { it.startsWith(query, ignoreCase = true) }
            .forEachIndexed { index, suggestion ->
                cursor.addRow(arrayOf(index, suggestion))
            }
        adapter.changeCursor(cursor)
    }

    private fun fetchWeatherData(cityName: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)

        val response = retrofit.getWeatherData(cityName, "e39cd138c15bf6064a5ff70b74966b86", "metric")
        response.enqueue(object : Callback<WeatherApp> {
            override fun onResponse(call: Call<WeatherApp?>, response: Response<WeatherApp?>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val temperature = responseBody.main.temp.toString()
                    val humidity = responseBody.main.humidity.toString()
                    val windSpeed = responseBody.wind.speed
                    val sunrise = responseBody.sys.sunrise.toLong()
                    val sunset = responseBody.sys.sunset.toLong()
                    val seaLevel = responseBody.main.pressure
                    val condition = responseBody.weather.firstOrNull()?.main ?: "unknown"
                    val maxTemp = responseBody.main.temp_max
                    val minTemp = responseBody.main.temp_min

                    binding.temp.text = "$temperature °C"
                    binding.weather.text = condition
                    binding.maxTemp.text = "Max Temp: $maxTemp °C"
                    binding.minTemp.text = "Min Temp: $minTemp °C"
                    binding.humidity.text = "$humidity %"
                    binding.windSpeed.text = "$windSpeed m/s"
                    binding.sunRise.text = "${time(sunrise)}"
                    binding.sunset.text = "${time(sunset)}"
                    binding.sea.text = "$seaLevel hPa"
                    binding.condition.text = condition
                    binding.day.text = dayName(System.currentTimeMillis())
                    binding.date.text = date()
                    binding.cityName.text = "$cityName "

                    changeImageAccordingToWeatherCondition(condition)
                }
            }

            private fun changeImageAccordingToWeatherCondition(conditions: String) {
                when (conditions) {
                    "Clear Sky", "Sunny", "Clear" -> {
                        binding.root.setBackgroundResource(R.drawable.sunny_background)
                        binding.lottieAnimationView.setAnimation(R.raw.sun)
                    }
                    "Party Clouds", "Clouds", "Overcast", "Mist", "Foggy" -> {
                        binding.root.setBackgroundResource(R.drawable.colud_background)
                        binding.lottieAnimationView.setAnimation(R.raw.cloud)
                    }
                    "Light Rains", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" -> {
                        binding.root.setBackgroundResource(R.drawable.rain_background)
                        binding.lottieAnimationView.setAnimation(R.raw.rain)
                    }
                    "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard" -> {
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

            override fun onFailure(call: Call<WeatherApp?>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun time(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp * 1000))
    }

    private fun dayName(timeInMillis: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(timeInMillis)
    }

    private fun date(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(System.currentTimeMillis())
    }
}
