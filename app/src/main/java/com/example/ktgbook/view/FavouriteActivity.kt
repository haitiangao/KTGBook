package com.example.ktgbook.view

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.ktgbook.R
import com.example.ktgbook.adapter.FavouriteAdapter
import com.example.ktgbook.customview.HappyFaceView
import com.example.ktgbook.model.Book
import com.example.ktgbook.model.FavouriteBook
import com.example.ktgbook.util.DebugLogger
import com.example.ktgbook.viewmodel.BookViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_favourite.*
import java.util.*

class FavouriteActivity : AppCompatActivity(), FavouriteAdapter.UserClickListener {
    private lateinit var viewModel: BookViewModel
    private var compositeDisposable = CompositeDisposable()
    private var books: MutableList<Book> = ArrayList()
    private var favouriteBooks: MutableList<FavouriteBook> = ArrayList()
    private lateinit var favouriteAdapter: FavouriteAdapter
    @BindView(R.id.favouriteListRecycler)
    lateinit var favouriteListRecycler: RecyclerView
    private lateinit var happyface: HappyFaceView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        ButterKnife.bind(this)

        (slide_background as View).setBackgroundResource(R.drawable.image_slideshow)

        val animationDrawable = ((slide_background as View).background as AnimationDrawable)
        animationDrawable.start()

        happyface = HappyFaceView(this);

        viewModel = ViewModelProvider(this).get<BookViewModel>(BookViewModel::class.java)
        //DebugLogger.logDebug(""+favouriteBooks.size());
        val itemDecoration =
            DividerItemDecoration(this, RecyclerView.VERTICAL)
        favouriteListRecycler.layoutManager = LinearLayoutManager(this)
        favouriteListRecycler.adapter = FavouriteAdapter(books, this, this)
        favouriteListRecycler.addItemDecoration(itemDecoration)
        favouriteBooks.clear()
        compositeDisposable.add(
            viewModel.allFavourites().subscribe { favourites ->
                for (element in favourites){
                    favouriteBooks.add(element)
                    DebugLogger.logDebug(element.toString())
                }
                findAllFavourite()
            })

    }

    private fun findAllFavourite() {
        DebugLogger.logDebug("Running favourites:" +favouriteBooks.size)

        if(favouriteBooks.size >0) {
            for (i in 0 until favouriteBooks.size) {
                compositeDisposable.add(
                    viewModel.getSpecificBookRx(favouriteBooks[i].id).subscribe({bookResult ->

                        books.add(bookResult)
                        favouriteListRecycler.adapter = null
                        favouriteAdapter = FavouriteAdapter(books, this, this)
                        favouriteListRecycler.adapter = favouriteAdapter
                        favouriteAdapter.notifyDataSetChanged()

                    }, { throwable ->
                        DebugLogger.logError(throwable)

                    }))
            }
        }
    }

    private fun getNewFavourites(favouriteBooks: List<FavouriteBook>) {
        favouriteListRecycler.adapter = null
        if(favouriteBooks.isNotEmpty()) {
            for (element in favouriteBooks) {

                compositeDisposable.add(
                    viewModel.getSpecificBookRx(element.id).subscribe({ bookResult ->

                        books.add(bookResult)
                        favouriteAdapter = FavouriteAdapter(books, this, this)
                        favouriteListRecycler.adapter = favouriteAdapter
                        favouriteAdapter.notifyDataSetChanged()

                    }, { throwable ->
                        DebugLogger.logError(throwable)
                    }))
            }
        }
    }


    @OnClick(R.id.backToPrevious)
    fun backToPrevious() {
        compositeDisposable.clear()
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun unfavouriteButton(book: Book) {
        viewModel.deleteAFavourite(book)
        //DebugLogger.logDebug("1: "+favouriteBooks.size());
        //FavouriteBook favouriteBook = new FavouriteBook(book.getId());
        for (i in 0 until favouriteBooks.size) {
            if (favouriteBooks[i].id == book.id) {
                favouriteBooks.removeAt(i)
                break
            }
        }
        //DebugLogger.logDebug("2: "+favouriteBooks.size());
        books.clear()
        getNewFavourites(favouriteBooks)
    }

}