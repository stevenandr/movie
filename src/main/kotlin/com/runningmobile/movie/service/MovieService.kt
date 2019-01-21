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

//        results.add(Movie(14,"Jaws","http://","Great!"))
//        results.add(Movie(14,"Cow","http://","Great!"))
//        results.add(Movie(14,"Dog","http://","Great!"))

        if (rawResults != null){
            rawResults.results.forEach {
                results.add(makeMovieFromSearchResult(it))
            }
        }

        return results
    }

    private fun makeMovieFromSearchResult(searchResult: SearchResult): Movie {
        return Movie(searchResult.id, searchResult.title, makePosterUrl(searchResult.posterPath), searchResult.popularity.toString())
    }

    private fun makePosterUrl(posterpath: String?): String {
        return if (posterpath!= null){
            "dummybasedurl"+ posterpath
        }
        else {
            ""
        }
    }

}


/*
{
  "page": 1,
  "total_results": 41,
  "total_pages": 3,
  "results": [
    {
      "vote_count": 4445,
      "id": 578,
      "video": false,
      "vote_average": 7.6,
      "title": "Jaws",
      "popularity": 22.554,
      "poster_path": "/l1yltvzILaZcx2jYvc5sEMkM7Eh.jpg",
      "original_language": "en",
      "original_title": "Jaws",
      "genre_ids": [
        27,
        53,
        12
      ],
      "backdrop_path": "/uTVuKo6OTGiead1ncsfH2klqYHC.jpg",
      "adult": false,
      "overview": "An insatiable great white shark terrorizes the townspeople of Amity Island, The police chief, an oceanographer and a grizzled shark hunter seek to destroy the bloodthirsty beast.",
      "release_date": "1975-06-18"
    },

 */
