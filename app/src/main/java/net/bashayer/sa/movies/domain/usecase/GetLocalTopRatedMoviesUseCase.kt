package net.bashayer.sa.movies.domain.usecase

import net.bashayer.sa.movies.data.Movie
import net.bashayer.sa.movies.domain.MovieLocalRepository
import rx.Observable

/**
 * Created by Bashayer Alsalman
 * Last modified on 3:37 PM
 */
class GetLocalTopRatedMoviesUseCase(movieLocalRepository: MovieLocalRepository) {

    var movieLocalRepository: MovieLocalRepository = movieLocalRepository

    fun execute(): Observable<List<Movie>> {
        return movieLocalRepository.getTopRatedMovies()
    }
}