package net.bashayer.sa.movies.data.dto

import net.bashayer.sa.movies.data.Results

/**
 * Created by Bashayer Alsalman
 * Last modified on 4:18 PM
 */

class MoviesResponseDto {
    var results: List<Results>? = null

    var page: String? = null

    var total_pages: String? = null

    var total_results: String? = null

    var status_message: String? = null

    var status_code: Int? = null

    override fun toString(): String {
        return "ClassPojo [results = $results, page = $page, total_pages = $total_pages, total_results = $total_results]"
    }
}
