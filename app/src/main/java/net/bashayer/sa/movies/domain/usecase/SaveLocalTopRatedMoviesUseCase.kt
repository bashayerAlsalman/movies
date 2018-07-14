package net.bashayer.sa.movies.domain.usecase

import net.bashayer.sa.movies.data.Movie
import net.bashayer.sa.movies.domain.MovieLocalRepository
import rx.Observable

/**
 * Created by Bashayer Alsalman
 * Last modified on 3:37 PM
 */
class SaveLocalTopRatedMoviesUseCase(movieLocalRepository: MovieLocalRepository) {

    var movieLocalRepository: MovieLocalRepository = movieLocalRepository

    fun execute(movies:List<Movie>): Observable<List<Movie>> {
        return movieLocalRepository.saveTopRatedMovies(movies)
    }
}