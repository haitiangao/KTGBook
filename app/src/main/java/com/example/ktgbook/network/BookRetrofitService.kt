package com.example.ktgbook.network

import com.example.ktgbook.model.Book
import com.example.ktgbook.model.Library
import com.example.ktgbook.util.Constants
import com.example.ktgbook.util.Constants.Companion.API_KEY
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BookRetrofitService {
    private val bookService: BookService
    private val client: OkHttpClient


    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createGoogleService(retrofitInstance: Retrofit): BookService {
        return retrofitInstance.create(BookService::class.java)
    }

    fun getBooksRx(query: String): Observable<Library> {
        return bookService.getBooksRx(query, API_KEY)
    }

    fun getSpecificBookRx(ID: String): Observable<Book> {
        return bookService.getSpecificBookRx(ID)
    }

    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        bookService = createGoogleService(getRetrofitInstance())
    }
}