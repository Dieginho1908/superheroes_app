package com.example.superheroesbase.domain.repository

import com.example.superheroesbase.domain.model.SuperHero

interface SuperHeroesRepository {
    suspend fun getSuperHeroes( fromId: Int, toId: Int ): List<SuperHero>
    suspend fun getSuperHeroById(superHeroId: String ): SuperHero?
}