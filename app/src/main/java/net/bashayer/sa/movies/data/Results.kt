package net.bashayer.sa.movies.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Bashayer Alsalman
 * Last modified on 4:19 PM
 */
class Results {
    @field:SerializedName("vote_average")
    var voteAverage: String? = null

    @field:SerializedName("backdrop_path")
    var backdropPath: String? = null

    @field:SerializedName("adult")
    var adult: String? = null

    @field:SerializedName("id")
    var id: String? = null

    @field:SerializedName("title")
    var title: String? = null

    @field:SerializedName("overview")
    var overview: String? = null

    @field:SerializedName("original_language")
    var originalLanguage: String? = null

    @field:SerializedName("genre_ids")
    var genreIds: Array<String>? = null

    @field:SerializedName("release_date")
    var releaseDate: String? = null

    @field:SerializedName("original_title")
    var originalTitle: String? = null

    @field:SerializedName("vote_count")
    var voteCount: String? = null

    @field:SerializedName("poster_path")
    var posterPath: String? = null

    @field:SerializedName("video")
    var video: String? = null

    @field:SerializedName("popularity")
    var popularity: String? = null

}
