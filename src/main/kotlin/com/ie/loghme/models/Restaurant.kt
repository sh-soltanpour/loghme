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
        var menu: List<Food>
)

data class Location(
        val x: Int,
        val y: Int
)

open class Food(
        val name: String,
        val description: String,
        val price: Int,
        val popularity: Double,
        val image: String
)

class FoodParty(
        val count: Int,
        val oldPrice: Int,
        name: String,
        description: String,
        price: Int,
        popularity: Double,
        image: String

) : Food(
        name,
        description,
        price,
        popularity,
        image
) {
    constructor(count: Int, newPrice: Int, food: Food) :
            this(count, food.price, food.name, food.description, newPrice, food.popularity, food.image)
}
