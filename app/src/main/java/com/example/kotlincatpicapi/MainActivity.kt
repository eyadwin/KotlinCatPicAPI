package com.example.kotlincatpicapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.koushikdutta.ion.Ion
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun getCatClick(view: View){
        Ion.with(this)
            .load("http://thecatapi.com/api/images/get?format=json&size=med&results_per_page=3")
            //.asJsonObject()
            .asString()
            .setCallback({ e, result ->
                // do stuff with the result or error
                Log.d("Iyad","The JSON data is \n$result")
                processCatData(result)
            })
    }
    /*
    [
  {
    "id": "ale",
    "url": "https://s3.us-west-2.amazonaws.com/cdn2.thecatapi.com/images/ale.gif",
    "source_url": "https://thecatapi.com/?image_id=ale"
  },..
]
     */
    fun processCatData(result:String){
        //add field name to the array, to be a valid JSON format
        val json = JSONObject("{\"images\":$result}")
        val imagesArray = json.getJSONArray("images")
        val firstImage = imagesArray.getJSONObject(0)
        val url = firstImage.getString("url")
        //get the image using Picasso library and place it into the imageView (catImage)
        Picasso.get()
            .load(url)
            .into(catImage)
    }
}
