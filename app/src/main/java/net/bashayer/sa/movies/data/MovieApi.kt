package net.bashayer.sa.movies.data

import net.bashayer.sa.movies.data.dto.MoviesResponseDto
import net.bashayer.sa.movies.domain.MovieApiRepository
import net.bashayer.sa.movies.domain.usecase.GetTopRatedMoviesUseCase
import retrofit2.Response
import rx.Observable
import rx.functions.Func1

/**
 * Created by Bashayer Alsalman
 * Last modified on 3:35 PM
 */
class MovieApi(val service: MovieApiService) : MovieApiRepository {


    override fun getTopRatedMovies(request: GetTopRatedMoviesUseCase.Request): Observable<MoviesResponseDto> {
        return service.getTopRatedMovies(request.apiKey, request.language, request.page, request.region,"application/json")
                .onErrorResumeNext(ThrowableObservableFunc1<MoviesResponseDto>())
                .flatMap(MoviesResponseObservableFunc1<MoviesResponseDto>())
                .map(Func1 { return@Func1 it })
    }


    class MoviesResponseObservableFunc1<T> : Func1<Response<T>, Observable<MoviesResponseDto>> {
        override fun call(response: Response<T>): Observable<MoviesResponseDto> {
            var responseDTO: MoviesResponseDto = if (response.body() != null) {
                response.body() as MoviesResponseDto
            } else {
                response.errorBody() as MoviesResponseDto
            }

            return if (response.isSuccessful) {
                Observable.just(responseDTO)
            } else {
                Observable.error(Exception(responseDTO.status_message))
            }
        }

    }


    internal class ThrowableObservableFunc1<T> : Func1<Throwable, Observable<Response<T>>> {
        override fun call(throwable: Throwable): Observable<Response<T>> {
            return Observable.error(Exception(throwable.message))
        }
    }
}