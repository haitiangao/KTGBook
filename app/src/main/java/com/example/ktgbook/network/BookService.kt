package com.example.ktgbook.network

import com.example.ktgbook.model.Book
import com.example.ktgbook.model.Library
import com.example.ktgbook.util.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookService {

    //"https://www.googleapis.com/books/v1/volumes?q={search term}&key={YOUR_API_KEY}”
    @GET(Constants.GET_URL_POSTFIX)
    fun getBooksRx(@Query("q") query: String, @Query("key") apiKey: String): Observable<Library>

    @GET(Constants.GET_URL_POSTFIX + "/{ID}")
    fun getSpecificBookRx(@Path("ID") ID: String): Observable<Book>
}