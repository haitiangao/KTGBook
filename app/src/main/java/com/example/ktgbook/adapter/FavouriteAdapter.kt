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
import com.example.ktgbook.adapter.FavouriteAdapter.FavouriteViewHolder
import com.example.ktgbook.model.Book


class FavouriteAdapter(
    private val books: List<Book>,
    private val context: Context,
    private val userClickListener: UserClickListener
) : RecyclerView.Adapter<FavouriteViewHolder>() {

    interface UserClickListener {
        fun unfavouriteButton(book: Book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourite_list_item, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        var authors = "N/A"
        val authorNumber: Int = books[position].volumeInfo.authors.size
        if (authorNumber > 0) {
            authors = ""
            for (i in 0 until authorNumber - 1) {
                authors += books[position].volumeInfo.authors[i].toString() + ", "
            }
            authors += books[position].volumeInfo.authors[authorNumber - 1]
        }
        holder.authorView.text = authors
        holder.titleView.text = books[position].volumeInfo.title
        Glide.with(context)
            .asBitmap()
            .load(books[position].volumeInfo.imageLinks.thumbnail)
            .placeholder(R.drawable.ic_broken_image_black_24dp)
            .into(holder.thumbNailView)
        holder.unfavouriteButton.setOnClickListener {
            userClickListener.unfavouriteButton(
                books[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

    inner class FavouriteViewHolder(itemView: View) :
        ViewHolder(itemView) {
        @BindView(R.id.thumbNailF)
        lateinit var thumbNailView: ImageView
        @BindView(R.id.titleF)
        lateinit var titleView: TextView
        @BindView(R.id.authorF)
        lateinit var authorView: TextView
        @BindView(R.id.unfavouriteThis)
        lateinit var unfavouriteButton: Button

        init {
            ButterKnife.bind(this, itemView)
        }
    }


}