package net.bashayer.sa.movies.presentaion.common

import android.content.Context
import android.preference.PreferenceManager.getDefaultSharedPreferences

/**
 * Created by Bashayer Alsalman
 * Last modified on 8:41 PM
 */
class LocalStorage(private val context: Context) {

    fun getFavouriteList(key: String): MutableList<String> {
        val sharedPreferences = getDefaultSharedPreferences(context)
        return sharedPreferences.getStringSet(key, mutableSetOf()).toMutableList()
    }

    fun setFavouriteList(key: String, favouriteList: MutableList<String>) {
        val editor = getDefaultSharedPreferences(context).edit()
        editor.putStringSet(key, favouriteList.toMutableSet())
        editor.commit()
    }
}