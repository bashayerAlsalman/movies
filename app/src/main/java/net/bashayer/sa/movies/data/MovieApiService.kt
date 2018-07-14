package net.bashayer.sa.movies.data

import net.bashayer.sa.movies.data.dto.MoviesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import rx.Observable

/**
 * Created by Bashayer Alsalman
 * Last modified on 3:35 PM
 */
interface MovieApiService {

    @Headers("Content-Type: application/json")
    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String,
                          @Query("language") language: String,
                          @Query("page") page: Int,
                          @Query("region") region: String,
                          @Header("Content-Type") h: String): Observable<Response<MoviesResponseDto>>


}