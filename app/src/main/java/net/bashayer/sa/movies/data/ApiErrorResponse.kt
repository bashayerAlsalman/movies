package net.bashayer.sa.movies.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Bashayer Alsalman
 * Last modified on 4:43 PM
 */
class ApiErrorResponse(@field:SerializedName("status_message") val status_message: String,
                       @field:SerializedName("status_code") val status_code: Int)
