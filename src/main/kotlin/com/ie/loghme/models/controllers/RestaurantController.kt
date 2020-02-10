package com.ie.loghme.models.controllers

import com.ie.loghme.models.Restaurant
import com.ie.loghme.models.repositories.RestaurantRepository
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
        return restaurantRepository.findAll().shuffled().take(30)
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
            restaurantRepository.saveAll(restaurants)
        }
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