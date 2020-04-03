package com.example.ktgbook.firebase

import com.example.ktgbook.model.FavouriteBook
import com.example.ktgbook.util.DebugLogger.logDebug
import com.google.firebase.database.*
import io.reactivex.Observable
import java.util.*

class FirebaseEvents {
    private val favouriteReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Favourite/")
    val favourites: Observable<List<FavouriteBook>>
        get() = Observable.just(favouriteBooks)


    fun setFavourite() {
        favouriteReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                favouriteBooks.clear()
                for (currentSnap in dataSnapshot.children) {
                    val currentFav = currentSnap.getValue(
                        FavouriteBook::class.java
                    )!!
                    favouriteBooks.add(currentFav)
                    logDebug("childname:$currentFav")
                }
                logDebug("Observable room2:  " + favouriteBooks.size)
            }

            override fun onCancelled(databaseError: DatabaseError) { //DebugLogger.logError(databaseError);
            }
        })
    }

    fun sendNewFavourite(favouriteBook: FavouriteBook?) {
        if (favouriteBook != null) {
            favouriteReference.child(favouriteBook.id).setValue(favouriteBook)
        }
    }

    fun removeFavourite(favouriteBook: FavouriteBook?) {
        if (favouriteBook != null) {
            favouriteReference.child(favouriteBook.id).removeValue()
        }
    }

    companion object {
        private var favouriteBooks: MutableList<FavouriteBook> =
            ArrayList()
    }

    init {
        setFavourite()
    }
}