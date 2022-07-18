package com.example.superheroesbase.domain.model

import com.example.superheroesbase.data.remote.dto.*

data class SuperHero(
    val appearance: Appearance,
    val biography: Biography,
    val connections: Connections,
    val id: String,
    val image: Image,
    val name: String,
    val powerStats: Powerstats,
    val work: Work
)
