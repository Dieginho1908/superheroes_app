package com.example.superheroesbase.data.remote.dto

import com.example.superheroesbase.domain.model.SuperHero
import com.google.gson.annotations.SerializedName

data class SuperHeroDTO(
    val appearance: Appearance,
    val biography: Biography,
    val connections: Connections,
    val id: String,
    val image: Image,
    val name: String,
    @SerializedName("powerstats")
    val powerStats: Powerstats,
    val response: String,
    val work: Work
)

fun SuperHeroDTO.toSuperHero(): SuperHero {
    return SuperHero(
        appearance = appearance,
        biography = biography,
        connections = connections,
        id = id,
        image = image,
        name = name,
        powerStats = powerStats,
        work = work
    )
}