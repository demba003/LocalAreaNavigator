package com.demba.localareanavigator.backend

import com.demba.localareanavigator.backend.models.Place
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PlacesController {

    @GetMapping("/places")
    fun places(): Array<Place> {
        val file = ResourceUtils.getFile("classpath:kampus.min.geojson")
        val place = Place("kampus", file.readText())
        return arrayOf(place, place, place, place)
    }

    @GetMapping("/places/{id}")
    fun place(@PathVariable id: String): String {
        return ResourceUtils.getFile("classpath:$id.min.geojson").readText()
    }
}