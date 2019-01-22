package com.runningmobile.movie.service

import com.runningmobile.movie.data.Movie
import com.runningmobile.movie.data.SearchResult
import com.runningmobile.movie.data.SearchResults
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

//https://api.themoviedb.org/3/search/movie?api_key=<<api_key>>&language=en-US&query=Jaws&page=1&include_adult=false
//https://api.themoviedb.org/3/search/movie?api_key=6e8cb0bd562aa83f390bfbc7efe9a009&language=en-US&query=jaws&page=1&include_adult=false

@Service
class MovieService {

    private val baseURL = "https://api.themoviedb.org/3/search/movie?language=en-USpage=1&include_adult=false"
    private val testUrl = "https://api.themoviedb.org/3/search/movie?api_key=6e8cb0bd562aa83f390bfbc7efe9a009&language=en-US&query=jaws&page=1&include_adult=false"
    private val apiKey = "6e8cb0bd562aa83f390bfbc7efe9a009"
    private val restTemplate = makeRestTemplate(RestTemplateBuilder())

    fun makeRestTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }

    fun searchByName (searchTerm: String): List<Movie>  {
        val rawResults = getSearchResultsFromApi(searchTerm)
        return convertApiResultsToOurMovieDomain(rawResults)
    }

    private fun getSearchResultsFromApi(searchTerm: String): SearchResults? {

        return restTemplate.getForObject(
                "https://api.themoviedb.org/3/search/movie?api_key=6e8cb0bd562aa83f390bfbc7efe9a009&language=en-US&query=$searchTerm&page=1&include_adult=false",
                SearchResults::class.java,
                getSearchOptions(searchTerm))
    }

    private fun getSearchOptions(searchTerm: String): MutableMap<String, *> {
        val options = mutableMapOf<String,String>()
        options.put("api_key", apiKey)
        options.put("query",searchTerm)
        return options
    }

    private fun convertApiResultsToOurMovieDomain(rawResults: SearchResults?): List<Movie> {
        var results = mutableListOf<Movie>()



        if (rawResults != null){
            var i = 1
            rawResults.results.forEach {
                if ( i < 11) {
                    results.add(makeMovieFromSearchResult(it))
                    i++
                }
            }
        }

        return results
    }

    private fun makeMovieFromSearchResult(searchResult: SearchResult): Movie {
        return Movie(searchResult.id, searchResult.title, makePosterUrl(searchResult.posterPath), searchResult.popularity.toString())
    }

    private fun makePosterUrl(posterpath: String?): String {
        return if (posterpath!= null){
            "https://image.tmdb.org/t/p/w154"+ posterpath
        }
        else {
            ""
        }
    }

}



