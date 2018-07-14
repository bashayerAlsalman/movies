package net.bashayer.sa.movies.presentaion.viewmovies

import net.bashayer.sa.movies.data.Movie

/**
 * Created by Bashayer Alsalman
 * Last modified on 5:22 PM
 */
interface MoviesView {
    fun showLoadProgress()
    fun hideLoadProgress()
    fun showMessage(message: String)
    fun showMessage(message: Int)
    fun updateTheList(movies: List<Movie>)
    fun saveMovieInLocalStorage()
}