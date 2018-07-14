package net.bashayer.sa.movies.presentaion

import android.app.Application
import net.bashayer.sa.movies.di.moviesAppModule
import org.koin.android.ext.android.startKoin

/**
 * Created by Bashayer Alsalman
 * Last modified on 12:45 PM
 */
class MoviesApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(moviesAppModule))
    }
}