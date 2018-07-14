package net.bashayer.sa.movies.di

import android.arch.persistence.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.bashayer.sa.movies.data.*
import net.bashayer.sa.movies.domain.MovieApiRepository
import net.bashayer.sa.movies.domain.MovieLocalRepository
import net.bashayer.sa.movies.domain.usecase.GetLocalTopRatedMoviesUseCase
import net.bashayer.sa.movies.domain.usecase.GetTopRatedMoviesUseCase
import net.bashayer.sa.movies.domain.usecase.SaveLocalTopRatedMoviesUseCase
import net.bashayer.sa.movies.presentaion.common.LocalStorage
import net.bashayer.sa.movies.presentaion.viewmovies.ViewMoviesPresenter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Bashayer Alsalman
 * Last modified on 12:47 PM
 */

val moviesAppModule: Module = applicationContext {
    bean { ViewMoviesPresenter(get(), get(), get(), AndroidSchedulers.mainThread(), Schedulers.io()) }
    bean { GetTopRatedMoviesUseCase(get()) }
    bean { GetLocalTopRatedMoviesUseCase(get()) }
    bean { SaveLocalTopRatedMoviesUseCase(get()) }

    bean { MovieLocalStorageImpl(get()) as MovieLocalRepository }
    bean { Room.databaseBuilder(get(), AppDatabase::class.java, "database-name").allowMainThreadQueries().build()  }
    bean { get<AppDatabase>().movieDao() }

    bean { MovieApi(get()) as MovieApiRepository}


    bean { LocalStorage(get()) }

    bean ("httpLoggingInterceptor"){ httpLoggingInterceptor() }
    bean { okHttpClient(get()) }
    bean { gson() }
    bean { errorResponseBodyConverter(get()) }
    bean { rxJavaRetrofit(get(), get()) }
    bean { createMovieApi(get()) }
    //  bean{}
}
fun createMovieApi(retrofit: Retrofit): MovieApiService {
    return retrofit.create(MovieApiService::class.java)
}

fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor {
                var requestBuilder = it.request().newBuilder()
                requestBuilder.addHeader("Content-Type", "application/json")
                return@Interceptor it.proceed(requestBuilder.build())
            })
            .build()
}

fun gson(): Gson {
    return GsonBuilder().create()
}

fun errorResponseBodyConverter(retrofit: Retrofit): Converter<ResponseBody, ApiErrorResponse> {
    return retrofit.responseBodyConverter<ApiErrorResponse>(ApiErrorResponse::class.java, arrayOfNulls(0))
}


fun rxJavaRetrofit(okHttpClient: OkHttpClient,
                   gson: Gson): Retrofit {
    val apiEndpoint = "https://api.themoviedb.org/3/"//todo inject properties here

    return Retrofit.Builder()
            .baseUrl(apiEndpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
}