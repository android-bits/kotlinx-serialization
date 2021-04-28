package org.androidbits.kotlinx_serialization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.androidbits.kotlinx_serialization.data.RickAndMortyAPi
import org.androidbits.kotlinx_serialization.data.RickAndMortyRepoImpl
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchData()
    }

    fun fetchData() {
        val baseUrl =  "https://rickandmortyapi.com/api/"
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
        val api = retrofit.create(RickAndMortyAPi::class.java)
        val repo = RickAndMortyRepoImpl(api)
        runBlocking {
            val characters = repo.getCharacters()
            for (character in characters) {
                println(character.name)
            }
        }
    }
}