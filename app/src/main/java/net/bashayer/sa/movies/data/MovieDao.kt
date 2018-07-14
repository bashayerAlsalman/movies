package net.bashayer.sa.movies.data

import android.arch.persistence.room.*


/**
 * Created by Bashayer Alsalman
 * Last modified on 10:05 PM
 */
@Dao
interface MovieDao {
    @get:Query("SELECT * FROM movie")
    val all: List<Movie>

    @Insert
    fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM movie")
    fun deleteAll()
}

@Database(entities = arrayOf(Movie::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}