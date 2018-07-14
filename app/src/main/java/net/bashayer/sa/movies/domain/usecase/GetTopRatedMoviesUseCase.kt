package net.bashayer.sa.movies.domain.usecase

import net.bashayer.sa.movies.data.dto.MoviesResponseDto
import net.bashayer.sa.movies.domain.MovieApiRepository
import rx.Observable

/**
 * Created by Bashayer Alsalman
 * Last modified on 3:37 PM
 */
class GetTopRatedMoviesUseCase(movieApiRepository: MovieApiRepository) {

    var movieApiRepository: MovieApiRepository = movieApiRepository

    var request: GetTopRatedMoviesUseCase.Request? = null

    fun execute(request: GetTopRatedMoviesUseCase.Request): Observable<MoviesResponseDto> {
        return movieApiRepository.getTopRatedMovies(request)
    }

    class Request(val apiKey: String, val language: String, val page: Int, val region:String)

}