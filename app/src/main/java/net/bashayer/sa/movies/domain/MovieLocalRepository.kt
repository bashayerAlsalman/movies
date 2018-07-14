package net.bashayer.sa.movies.domain

import net.bashayer.sa.movies.data.Movie
import rx.Observable

/**
 * Created by Bashayer Alsalman
 * Last modified on 3:39 PM
 */
interface MovieLocalRepository {
    fun getTopRatedMovies(): Observable<List<Movie>>
    fun saveTopRatedMovies(movies:List<Movie>): Observable<List<Movie>>
}