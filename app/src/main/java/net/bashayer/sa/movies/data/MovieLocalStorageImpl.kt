package net.bashayer.sa.movies.data

import net.bashayer.sa.movies.domain.MovieLocalRepository
import rx.Observable

/**
 * Created by Bashayer Alsalman
 * Last modified on 10:07 PM
 */
class MovieLocalStorageImpl(val movieDao: MovieDao) : MovieLocalRepository {

    override fun saveTopRatedMovies(movies: List<Movie>): Observable<List<Movie>> {
        movieDao.deleteAll()
        movieDao.insertAll(movies)
        return Observable.just(movies)
    }

    override fun getTopRatedMovies(): Observable<List<Movie>> {
        return Observable.just(movieDao.all)
    }
}