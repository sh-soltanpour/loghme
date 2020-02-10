package com.ie.loghme.repositories

import com.ie.loghme.models.Restaurant
import org.springframework.data.mongodb.repository.MongoRepository

interface RestaurantRepository: MongoRepository<Restaurant, Int> {
}