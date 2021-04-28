package org.androidbits.kotlinx_serialization

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import okhttp3.MediaType.Companion.toMediaType
import org.androidbits.kotlinx_serialization.data.Location
import org.androidbits.kotlinx_serialization.data.RickAndMortyAPi
import org.androidbits.kotlinx_serialization.data.RickAndMortyRepoImpl
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import retrofit2.Retrofit

class RickAndMortyRepoTest {

    private val baseUrl =  "https://rickandmortyapi.com/api/"

    @Test
    fun `test data is fetched from the API`() = runBlocking {
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
        val api = retrofit.create(RickAndMortyAPi::class.java)
        val repo = RickAndMortyRepoImpl(api)
        val characters = repo.getCharacters()
        assertThat(characters.size, `is`(20))
    }

    @Test
    fun `test encoding object to string`() {
        val location = Location(name = "Test", url = "https://test.com")
        val serialized = Json.encodeToString(location)
        assertThat(serialized, `is`("""{"name":"Test","url":"https://test.com"}"""))
    }

    @Test
    fun `test decoding string to object`() {
        val string = """{"name":"Test","url":"https://test.com"}"""
        val location = Json.decodeFromString<Location>(string)
        assertThat(location, `is`(Location(name = "Test", url = "https://test.com")))
    }

    @Test
    fun `test building json object`() {
        val objects = mapOf(
            "name" to JsonPrimitive("test"),
            "cars" to JsonArray(listOf(
                JsonObject(mapOf(
                    "color" to JsonPrimitive("blue"),
                    "type" to JsonPrimitive("sedan")
                )),
                JsonObject(mapOf(
                    "color" to JsonPrimitive("blue"),
                    "type" to JsonPrimitive("suv")
                ))
            ))
        )
        val jsonObject = JsonObject(objects)

        assertThat(jsonObject.keys.size, `is`(2))
    }
}