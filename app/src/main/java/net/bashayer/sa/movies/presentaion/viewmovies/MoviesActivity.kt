package net.bashayer.sa.movies.presentaion.viewmovies

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.novoda.merlin.Merlin
import kotlinx.android.synthetic.main.activity_movies.*
import net.bashayer.sa.movies.R
import net.bashayer.sa.movies.data.Movie
import net.bashayer.sa.movies.presentaion.common.LocalStorage
import org.koin.android.ext.android.inject


/**
 * Created by Bashayer Alsalman
 * Last modified on 2:09 PM
 */
class MoviesActivity : AppCompatActivity(), MoviesView, MovieClickListener {

    val presenter: ViewMoviesPresenter by inject()
    val localStorage: LocalStorage by inject()

    private var adapter: MoviesAdapter? = null
    private var movies: List<Movie> = arrayListOf()
    private var favouriteMovies: MutableList<String> = mutableListOf()

    lateinit var merlin: Merlin

    override fun onResume() {
        super.onResume()
        merlin.bind()
    }

    private val KEY = "favouriteList"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        favouriteMovies = localStorage.getFavouriteList(KEY)

        initRecyclerView()
        presenter.bindView(this)

        merlin = Merlin.Builder().withConnectableCallbacks().build(this)

        presenter.loadLocalTopRatedMovies()
        merlin.registerConnectable { presenter.loadTopRatedMovies() }
    }

    override fun onPause() {
        merlin.unbind()
        super.onPause()
        localStorage.setFavouriteList(KEY, favouriteMovies)
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        adapter = MoviesAdapter(this, movies, favouriteMovies, this@MoviesActivity)
        recyclerView.adapter = adapter
    }

    override fun showLoadProgress() {
        loadingSpinner.visibility = View.VISIBLE
    }

    override fun hideLoadProgress() {
        loadingSpinner.visibility = View.GONE
    }

    override fun onViewMovieDetails(movie: Movie) {
        val layoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.movie_details, null)

        val releaseDate = view.findViewById(R.id.releaseDate) as TextView
        val voting = view.findViewById(R.id.voting) as TextView
        val overview = view.findViewById(R.id.overview) as TextView

        releaseDate.text = movie.releaseDate
        voting.text = movie.rating
        overview.text = movie.overview

        MaterialDialog.Builder(this@MoviesActivity)
                .title(movie.title)
                .customView(view, false)
                .backgroundColor(resources.getColor(R.color.white))
                .contentColor(resources.getColor(R.color.black))
                .titleColor(resources.getColor(R.color.black))
                .show()
    }

    override fun saveMovieInLocalStorage() {
        presenter.saveLocalTopRatedMovies(movies)
    }

    override fun onFavouriteButtonClicked(button: ImageView, id: String) {
        if (favouriteMovies.contains(id)) {
            button.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.img_star_gray))
            favouriteMovies.remove(id)
        } else {
            button.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.img_star_yellow))
            favouriteMovies.add(id)
        }
    }

    override fun showMessage(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }

    override fun showMessage(message: Int) {
        showMessage(getString(message))
    }

    override fun updateTheList(results: List<Movie>) {
        movies = results
        adapter?.movies = movies
        adapter?.notifyDataSetChanged()
    }

}