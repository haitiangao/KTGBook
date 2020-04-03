package com.example.ktgbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ktgbook.firebase.FirebaseEvents
import com.example.ktgbook.model.Book
import com.example.ktgbook.model.FavouriteBook
import com.example.ktgbook.model.Library
import com.example.ktgbook.network.BookRetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class BookViewModel(application: Application) : AndroidViewModel(application) {
    private val bookRetrofitService: BookRetrofitService = BookRetrofitService()
    private var firebaseEvent: FirebaseEvents = FirebaseEvents()

    fun getBookListRx(query: String): Observable<Library> {
        return bookRetrofitService
            .getBooksRx(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getSpecificBookRx(ID: String): Observable<Book> {
        return bookRetrofitService
            .getSpecificBookRx(ID)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun allFavourites(): Observable<List<FavouriteBook>>{
        return firebaseEvent.favourites
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }


    fun insertAFavourite(book: Book) {
        val favouriteBook = FavouriteBook(book.id)
        firebaseEvent.sendNewFavourite(favouriteBook)
        //bookRepository.insertAFavourite(favouriteBook)
    }

    fun deleteAFavourite(book: Book) {
        val favouriteBook = FavouriteBook(book.id)
        firebaseEvent.removeFavourite(favouriteBook)
        //bookRepository.deleteAFavourite(favouriteBook)
        //DebugLogger.logDebug("Model fav: "+bookRepository.getAllFavourites().size());
    }

    fun disposeDisposables() {

    }

    init {
        firebaseEvent.setFavourite()

    }
}