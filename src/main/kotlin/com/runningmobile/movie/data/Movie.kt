package com.runningmobile.movie.data

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

//@JsonNaming(PropertyNamingStrategy.SNAKE_CASE.javaClass)
data class Movie (
        @JsonProperty("movie_id")  val movieId : Int,
        @JsonProperty("title") val title : String,
        @JsonProperty("poster_image_url") val posterImageUrl : String,
        @JsonProperty("popularity_summary") val popularitySummary : String
)
