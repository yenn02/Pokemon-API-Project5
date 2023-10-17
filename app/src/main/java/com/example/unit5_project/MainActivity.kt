package com.example.unit5_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var pokeImageURL = ""
    var pokeHeight = ""
    var pokeName = ""
    var id = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val imageView = findViewById<ImageView>(R.id.pokemonImage)
        val button= findViewById<Button>(R.id.pickButton)
        val name = findViewById<TextView>(R.id.nameInfo)
        val height = findViewById<TextView>(R.id.heightInfo)

        getNextImage(button, imageView, name, height)
    }
    private fun getNextImage(button: Button, imageView: ImageView, name: TextView, height: TextView){
        getPokemonImageURL(id)
        button.setOnClickListener {
            id = (1..20).random()


            Glide.with(this)
                .load(pokeImageURL)
                .fitCenter()
                .into(imageView)
                 name.setText("Name: $pokeName")
                 height.setText("Height: $pokeHeight")

        }
    }


    private fun getPokemonImageURL(id: Int){
        val client = AsyncHttpClient()

        client["https://pokeapi.co/api/v2/pokemon/${id}", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {


                pokeImageURL = json.jsonObject.getJSONObject("sprites").getString("front_default")
                pokeName = json.jsonObject.getString("name")
                pokeHeight = json.jsonObject.getString("height")


            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Pokemon Error", errorResponse)
            }
        }]
    }



}