package org.androidbits.kotlinx_serialization.data

import retrofit2.http.GET

interface RickAndMortyAPi {

    @GET("character")
    suspend fun getCharacters(): CharactersResponse
}


interface RickAndMortyRepo {
    suspend fun getCharacters(): List<Character>
}


class RickAndMortyRepoImpl(private val rickAndMortyAPi: RickAndMortyAPi) : RickAndMortyRepo {

    override suspend fun getCharacters(): List<Character> {
        val response = rickAndMortyAPi.getCharacters()
        return response.results
    }
}