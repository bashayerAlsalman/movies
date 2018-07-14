package net.bashayer.sa.movies.presentaion.viewmovies

import android.widget.ImageView
import net.bashayer.sa.movies.data.Movie

/**
 * Created by Bashayer Alsalman
 * Last modified on 1:07 PM
 */
interface MovieClickListener {
    fun onViewMovieDetails(movie: Movie)
    fun onFavouriteButtonClicked(button: ImageView, id:String)

}