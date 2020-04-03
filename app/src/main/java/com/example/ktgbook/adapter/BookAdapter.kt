package com.example.ktgbook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.example.ktgbook.R
import com.example.ktgbook.adapter.BookAdapter.BookViewHolder
import com.example.ktgbook.model.Book


class BookAdapter(
    private val books: List<Book>,
    private val context: Context,
    private val userClickListener: UserClickListener
) : RecyclerView.Adapter<BookViewHolder>() {


    interface UserClickListener {
        fun favouriteButton(book: Book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        var authors = "N/A"
        val authorNumber: Int = if (books[position].volumeInfo.authors.isNullOrEmpty())
            0
        else
            books[position].volumeInfo.authors.size

        if (authorNumber > 0) {
            authors = ""
            for (i in 0 until authorNumber - 1) {
                authors += books[position].volumeInfo.authors[i].toString() + ", "
            }
            authors += books[position].volumeInfo.authors[authorNumber - 1]
        }
        holder.authorView.text = authors
        holder.titleView.text = books[position].volumeInfo.title

        if (books[position].volumeInfo.imageLinks != null) {
            Glide.with(context)
                .asBitmap()
                .load(books[position].volumeInfo.imageLinks.thumbnail)
                .placeholder(R.drawable.ic_broken_image_black_24dp)
                .into(holder.thumbNailView)
            }
        else {
            Glide.with(context)
                .asBitmap()
                .load(R.drawable.ic_broken_image_black_24dp)
                .into(holder.thumbNailView)
        }

        holder.favouriteButton.setOnClickListener {
            userClickListener.favouriteButton(
                books[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

    inner class BookViewHolder(itemView: View) : ViewHolder(itemView) {
        @BindView(R.id.thumbNail)
        lateinit var thumbNailView: ImageView
        @BindView(R.id.title)
        lateinit var titleView: TextView
        @BindView(R.id.author)
        lateinit var authorView: TextView
        @BindView(R.id.favouriteThis)
        lateinit var favouriteButton: Button

        init {
            ButterKnife.bind(this, itemView)
        }
    }

}