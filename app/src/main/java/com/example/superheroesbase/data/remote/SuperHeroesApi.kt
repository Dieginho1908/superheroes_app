package com.example.superheroesbase.data.remote

import com.example.superheroesbase.data.remote.dto.SuperHeroDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperHeroesApi {
    @GET("{superHeroId}")
    suspend fun getSuperHeroById(@Path("superHeroId") superHeroId: String): SuperHeroDTO
}