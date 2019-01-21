package com.runningmobile.movie.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchResults(
        @JsonProperty("page") val page: Int,
        @JsonProperty("total_results") val totalResults: Int,
        @JsonProperty("total_pages") val totalPages: Int,
        @JsonProperty("results") val results: List<SearchResult>
    )
