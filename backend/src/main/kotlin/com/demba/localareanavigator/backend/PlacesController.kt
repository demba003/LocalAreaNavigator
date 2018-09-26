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
        val url = "https://api.mapbox.com/styles/v1/mapbox/streets-v10/static/19.942,50.0719,16/600x400?access_token=pk.eyJ1IjoiZGVtYmEiLCJhIjoiY2pibWo3cW43M2I5eDM0cjY0eG4zY2JxZyJ9.SFBv4D82Ih54yJHF5U__BQ"
        val place = Place("kampus", null, url)
        return arrayOf(place, place, place, place)
    }

    @GetMapping("/places/{id}")
    fun place(@PathVariable id: String): Place {
        val file = ResourceUtils.getFile("classpath:$id.min.geojson")
        val url = "https://api.mapbox.com/styles/v1/mapbox/streets-v10/static/19.942,50.0719,16/600x400?access_token=pk.eyJ1IjoiZGVtYmEiLCJhIjoiY2pibWo3cW43M2I5eDM0cjY0eG4zY2JxZyJ9.SFBv4D82Ih54yJHF5U__BQ"
        return Place(id, file.readText(), url)
    }
}