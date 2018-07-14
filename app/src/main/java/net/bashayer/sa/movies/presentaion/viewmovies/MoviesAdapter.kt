package net.bashayer.sa.movies.presentaion.viewmovies

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import net.bashayer.sa.movies.R
import net.bashayer.sa.movies.data.Movie

/**
 * Created by Bashayer Alsalman
 * Last modified on 1:03 PM
 */
class MoviesAdapter(context: Context, movies: List<Movie>, favouriteMovies: MutableList<String>, listener: MovieClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var context: Context = context
    var movies: List<Movie> = movies
    var favouriteMovies: MutableList<String> = favouriteMovies
    var listener: MovieClickListener = listener

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as MoviesViewHolder
        val movie = movies.get(position)

        viewHolder.apply {

            movieNameTextView.text = movie.title

            if (!movie.poster.isNullOrEmpty())
                Glide.with(context).load("https://image.tmdb.org/t/p/w500" + movie.poster).into(posterImageView)

            if (favouriteMovies.contains(movie.id)) {
                favouriteButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.img_star_yellow))
            }
            rootView.setOnClickListener { listener.onViewMovieDetails(movie) }
            favouriteButton.setOnClickListener {
                listener.onFavouriteButtonClicked(favouriteButton, movie.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var rootView: LinearLayout = view.findViewById(R.id.rootView)
        var movieNameTextView: TextView = view.findViewById(R.id.movieName)
        var posterImageView: ImageView = view.findViewById(R.id.moviePoster)
        var favouriteButton: ImageView = view.findViewById(R.id.favouriteButton)

    }
}