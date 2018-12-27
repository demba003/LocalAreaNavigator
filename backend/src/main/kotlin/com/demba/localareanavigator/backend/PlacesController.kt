package com.demba.localareanavigator.backend

import com.demba.localareanavigator.backend.models.Place
import org.springframework.web.bind.annotation.*
import java.io.File

@RestController
class PlacesController {

    @GetMapping("/place")
    fun getPlaces(): List<Place> {
        val url = "https://api.mapbox.com/styles/v1/mapbox/streets-v10/static/19.942,50.0719,16/600x400?access_token=pk.eyJ1IjoiZGVtYmEiLCJhIjoiY2pibWo3cW43M2I5eDM0cjY0eG4zY2JxZyJ9.SFBv4D82Ih54yJHF5U__BQ"

        return File(".")
                .listFiles()
                .asSequence()
                .filter { it.name.contains("json") }
                .map { Place(it.name.substring(0, it.name.indexOf(".")), null, url) }
                .toList()
    }

    @GetMapping(value = ["/place/{name}"], produces = ["application/json"])
    fun getPlace(@PathVariable name: String): String {
        return File("$name.json").readText()
    }

    @PutMapping("/place")
    fun postPlace(@RequestBody place: Place) {
        File("${place.name}.json").writeText(place.toJson())
    }
}