package net.bashayer.sa.movies.domain

import net.bashayer.sa.movies.data.dto.MoviesResponseDto
import net.bashayer.sa.movies.domain.usecase.GetTopRatedMoviesUseCase
import rx.Observable

/**
 * Created by Bashayer Alsalman
 * Last modified on 3:38 PM
 */
interface MovieApiRepository {
    fun getTopRatedMovies(request: GetTopRatedMoviesUseCase.Request): Observable<MoviesResponseDto>
}