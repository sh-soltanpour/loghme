package com.ie.loghme.controllers

import com.ie.loghme.models.Delivery
import com.ie.loghme.models.Location
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.random.Random

@RestController
@RequestMapping("/deliveries")
class DeliveryController {

    @GetMapping
    fun getDeliveries(): List<Delivery> {
        if (Random.nextInt(0, 100) < 67)
            return emptyList()
        val deliveries = mutableListOf<Delivery>()
        repeat(Random.nextInt(1, 10)) {
            deliveries.add(
                    Delivery(
                            id = UUID.randomUUID().toString(),
                            location = Location(
                                    x = Random.nextInt(-300, 300),
                                    y = Random.nextInt(-300, 300)
                            ),
                            velocity = Random.nextInt(1, 10)
                    )
            )
        }
        return deliveries
    }
}
