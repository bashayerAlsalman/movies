package net.bashayer.sa.movies.presentaion.viewmovies

import net.bashayer.sa.movies.R
import net.bashayer.sa.movies.data.Movie
import net.bashayer.sa.movies.data.Results
import net.bashayer.sa.movies.data.dto.MoviesResponseDto
import net.bashayer.sa.movies.domain.usecase.GetLocalTopRatedMoviesUseCase
import net.bashayer.sa.movies.domain.usecase.GetTopRatedMoviesUseCase
import net.bashayer.sa.movies.domain.usecase.SaveLocalTopRatedMoviesUseCase
import rx.Scheduler
import rx.Subscriber
import rx.Subscription

/**
 * Created by Bashayer Alsalman
 * Last modified on 2:10 PM
 */
class ViewMoviesPresenter(val useCase: GetTopRatedMoviesUseCase,
                          val getLocalUseCase: GetLocalTopRatedMoviesUseCase,
                          val saveLocalUseCase: SaveLocalTopRatedMoviesUseCase,
                          val observeOn: Scheduler,
                          val subscribeOn: Scheduler) {

    lateinit var view: MoviesView
    private var getTopRatedSubscription: Subscription? = null
    private var getLocalSubscription: Subscription? = null
    private var saveLocalSubscription: Subscription? = null
    private var movies: List<Results>? = listOf()

    fun loadTopRatedMovies() {

        view.showLoadProgress()
        val request = GetTopRatedMoviesUseCase.Request("6762a7d673d80d22ed67d438c964c81f", "en-US", 1, "")
        getTopRatedSubscription = useCase.execute(request)
                .observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribe(GetMoviesSubscriber())
    }

    fun loadLocalTopRatedMovies() {
        view.showLoadProgress()
        getLocalSubscription = getLocalUseCase.execute()
                .observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribe(GetLocalMoviesSubscriber())
    }

    fun saveLocalTopRatedMovies(movies: List<Movie>) {
        saveLocalSubscription = saveLocalUseCase.execute(movies)
                .observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribe(SaveLocalMoviesSubscriber())
    }

    fun setMovies(results: List<Results>?) {
        if (results != null && results.isNotEmpty()) {
            movies = results
            view.updateTheList(mapToMovie(results))
        }
    }

    private fun mapToMovie(results: List<Results>): List<Movie> {
        val movies = mutableListOf<Movie>()
        results.forEach { result ->
            val movie = Movie(result.id!!, result.title!!, result.voteAverage!!, result.overview!!, result.releaseDate!!, result.posterPath!!)
            movies.add(movie)
        }
        return movies.toList()
    }

    fun setLocalMovies(movies: List<Movie>?) {
        if (movies != null && movies.isNotEmpty()) {
            view.updateTheList(movies)
        }
    }

    inner class GetMoviesSubscriber : Subscriber<MoviesResponseDto>() {
        override fun onError(e: Throwable?) {
            view.showMessage(e?.message + "")
            e?.printStackTrace()
        }

        override fun onNext(responseDto: MoviesResponseDto) {
            setMovies(responseDto.results)
        }

        override fun onCompleted() {
            view.hideLoadProgress()
            view.saveMovieInLocalStorage()
            view.showMessage(R.string.loadApi)
            getTopRatedSubscription?.unsubscribe()
        }

    }

    inner class GetLocalMoviesSubscriber : Subscriber<List<Movie>>() {
        override fun onNext(movies: List<Movie>?) {
            setLocalMovies(movies)
        }

        override fun onCompleted() {
            view.hideLoadProgress()
            view.showMessage(R.string.loadLocal)
            getLocalSubscription?.unsubscribe()
        }

        override fun onError(e: Throwable?) {
            view.hideLoadProgress()
            view.showMessage(e?.message + "")
        }

    }

    inner class SaveLocalMoviesSubscriber : Subscriber<List<Movie>>() {
        override fun onNext(movies: List<Movie>?) {
            //todo
        }

        override fun onCompleted() {
            view.hideLoadProgress()
            view.showMessage(R.string.savedLocally)
            saveLocalSubscription?.unsubscribe()
        }

        override fun onError(e: Throwable?) {
            view.hideLoadProgress()
            view.showMessage(e?.message + "")
        }

    }

    fun bindView(view: MoviesView) {
        this.view = view
    }
}