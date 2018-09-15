package com.demba.localareanavigator.backend

import com.demba.localareanavigator.backend.models.Place
import com.demba.localareanavigator.backend.models.Places
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PlacesController {

    @GetMapping("/places")
    fun places(): Places {
        val file = ResourceUtils.getFile("classpath:kampus.min.geojson")
        return Places(arrayOf(Place("kampus", file.readText())))
    }

    @GetMapping("/places/{id}")
    fun place(@PathVariable id: String): String {
        return ResourceUtils.getFile("classpath:$id.min.geojson").readText()
    }
}