package net.bashayer.sa.movies.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Bashayer Alsalman
 * Last modified on 1:37 PM
 */
@Entity
data class Movie(@PrimaryKey var id: String,
                 @ColumnInfo(name = "title")  var title: String,
                 @ColumnInfo(name = "rating")  var rating: String,
                 @ColumnInfo(name = "overview")  var overview: String,
                 @ColumnInfo(name = "release_date")  var releaseDate: String,
                 @ColumnInfo(name = "poster")  var poster: String)
