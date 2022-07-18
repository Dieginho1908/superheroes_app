package com.example.superheroesbase.data.repository

import android.util.Log
import com.example.superheroesbase.commons.Constants.SUCCESS_RESPONSE
import com.example.superheroesbase.core.RetrofitHelper
import com.example.superheroesbase.data.remote.SuperHeroesApi
import com.example.superheroesbase.data.remote.dto.toSuperHero
import com.example.superheroesbase.domain.model.SuperHero
import com.example.superheroesbase.domain.repository.SuperHeroesRepository

class SuperHeroesRepositoryImpl : SuperHeroesRepository {

    override suspend fun getSuperHeroes(fromId: Int, toId: Int): List<SuperHero> {
        val superHeroesList: MutableList<SuperHero> = arrayListOf()
        for (i in fromId..toId) {
            try {
                val apiService = RetrofitHelper.getRetrofit().create(SuperHeroesApi::class.java)
                val result = apiService.getSuperHeroById(i.toString())
                if (result.response == SUCCESS_RESPONSE) {
                    superHeroesList.add(result.toSuperHero())
                }
            } catch (e: Exception) {
                Log.d("Error", e.message ?: "Something went wrong")
            }
        }

        return superHeroesList
    }

    override suspend fun getSuperHeroById(superHeroId: String): SuperHero? {

         val response = try {
            val apiService = RetrofitHelper.getRetrofit().create(SuperHeroesApi::class.java)
            val result = apiService.getSuperHeroById(superHeroId)
            if (result.response == SUCCESS_RESPONSE) {
                result.toSuperHero()
            } else null
        } catch (e: Exception) {
            Log.d("Error", e.message ?: "Something went wrong")
            null
        }
        return response
    }
}