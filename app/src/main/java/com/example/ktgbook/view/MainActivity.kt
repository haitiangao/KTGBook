package com.example.ktgbook.view

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.ktgbook.R
import com.example.ktgbook.adapter.BookAdapter
import com.example.ktgbook.model.Book
import com.example.ktgbook.util.DebugLogger
import com.example.ktgbook.viewmodel.BookViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class MainActivity : AppCompatActivity(), BookAdapter.UserClickListener {
    private lateinit var viewModel: BookViewModel
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var query: String
    private var books: MutableList<Book> = ArrayList<Book>()
    @BindView(R.id.bookListRecycler)
    lateinit var bookListRecycler: RecyclerView
    @BindView(R.id.searchBookEdit)
    lateinit var searchBookEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        viewModel = ViewModelProvider(this).get<BookViewModel>(BookViewModel::class.java)
        val itemDecoration =
            DividerItemDecoration(this, RecyclerView.VERTICAL)
        bookListRecycler.layoutManager = LinearLayoutManager(this)
        bookListRecycler.adapter = BookAdapter(books, this, this)
        bookListRecycler.addItemDecoration(itemDecoration)
    }

    @OnClick(R.id.searchBookButton)
    fun searchBooks() {
        query = searchBookEdit.text.toString()
        compositeDisposable.add(viewModel.getBookListRx(query).subscribe({resultLibrary ->


            val bookResults: MutableList<Book> = resultLibrary.items
            val bookAdapter = BookAdapter(bookResults, this, this);
            bookListRecycler.adapter = null;
            bookListRecycler.adapter = bookAdapter;
            bookAdapter.notifyDataSetChanged();

        }, { throwable ->

            DebugLogger.logError(throwable);
        }));

    }


    override fun onResume() {
        super.onResume()
        searchBooks()
    }

    @OnClick(R.id.seeFavouriteButton)
    fun seeAllFavourite() {
        val intent = Intent(this, FavouriteActivity::class.java)
        startActivity(intent)
    }

    override fun favouriteButton(book: Book) {
        viewModel.insertAFavourite(book)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        viewModel.disposeDisposables()
    }
}