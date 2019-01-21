package com.runningmobile.movie.controller

import com.runningmobile.movie.data.Movie
import com.runningmobile.movie.service.MovieService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("movies")
class MovieController(private val movieService: MovieService) {

    @GetMapping
    fun search(@RequestParam("search") searchTerm: String): List<Movie> {
        return movieService.searchByName(searchTerm)
    }
}