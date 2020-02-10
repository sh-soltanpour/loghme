package com.ie.loghme.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "restaurants")
data class Restaurant(
        @Id
        var id: String? = null,
        val name: String,
        val location: Location,
        val logo: String?,
        val menu: List<Food>
)

data class Location(
        val x: Int,
        val y: Int
)
data class Food (
        val name: String,
        val description: String,
        val price: Int,
        val popularity: Double,
        val image: String
)