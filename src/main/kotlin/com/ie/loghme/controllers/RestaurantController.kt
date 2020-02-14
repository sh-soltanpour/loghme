package com.ie.loghme.controllers

import com.ie.loghme.models.Restaurant
import com.ie.loghme.repositories.RestaurantRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/restaurants")
class RestaurantController(
        val restaurantRepository: RestaurantRepository,
        @Value("\${secretToken}")
        val secretToken: String
) {

    @GetMapping
    fun getRestaurants(): List<Restaurant> {
        return restaurantRepository.findAll()
                .distinctBy { it.name }
                .filter { it.menu.isNotEmpty() }
                .shuffled()
                .take(30)
    }

    @PostMapping("/reload")
    fun reloadRestaurants(
            request: HttpServletRequest,
            response: HttpServletResponse,
            @RequestBody restaurants: List<Restaurant>
    ) {
        if (request.getHeader("X-Auth-Token") != secretToken)
            response.status = 403
        else {
            restaurants.forEach { it.menu = it.menu.distinctBy { food -> food.name } }
            restaurantRepository.saveAll(restaurants)
        }
    }

    @GetMapping("/duplicates")
    fun getDuplicateFoods(
            request: HttpServletRequest,
            response: HttpServletResponse
    ): List<Map<String, Int>> {
        if (request.getHeader("X-Auth-Token") != secretToken) {
            response.status = 403
            return emptyList()
        }
        return restaurantRepository.findAll()
                .map { it.menu.groupBy { food -> food.name } }
                .map { map -> map.mapValues { it.value.size } }
    }

    @DeleteMapping
    fun removeRestaurants(
            request: HttpServletRequest,
            response: HttpServletResponse
    ) {
        if (request.getHeader("X-Auth-Token") != secretToken)
            response.status = 403
        else {
            restaurantRepository.deleteAll()
        }
    }

}