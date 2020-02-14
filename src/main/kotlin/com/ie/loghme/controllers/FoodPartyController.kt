package com.ie.loghme.controllers

import com.ie.loghme.models.FoodParty
import com.ie.loghme.models.Restaurant
import com.ie.loghme.repositories.RestaurantRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.math.roundToInt
import kotlin.random.Random


@RestController
@RequestMapping("/foodparty")
class FoodPartyController(
        val restaurantRepository: RestaurantRepository
) {

    @GetMapping
    fun getFoods(): List<Restaurant> {
        return restaurantRepository.findAll()
                .shuffled()
                .take(Random.nextInt(1, 5))
                .map { restaurant ->
                    val food = restaurant.menu.shuffled().first()
                    val newPrice = (Random.nextDouble(0.1, 0.6) * food.price).roundToInt()
                    val foodParty = FoodParty(count = Random.nextInt(1, 5), newPrice = newPrice, food = food)
                    restaurant.menu = listOf(foodParty)
                    restaurant
                }
    }
}