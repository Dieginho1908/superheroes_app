package com.example.superheroesbase.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Connections(
    @SerializedName("group-affiliation")
    val groupAffiliation: String,
    val relatives: String
)