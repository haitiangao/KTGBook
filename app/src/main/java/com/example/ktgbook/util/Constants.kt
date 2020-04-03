package com.example.ktgbook.util

class Constants {
    companion object{
        //Error Messages
        const val TAG = "TAG_H"
        const val ERROR_PREFIX = "Error: "
        const val RESULTS_NULL = "Results were null"

        //Retrofit call
//"https://www.googleapis.com/books/v1/volumes?q={search term}&key={YOUR_API_KEY}”
        const val API_KEY = "AIzaSyDcbI1nywoh1Yud7irvxJ5FPImZl0KIWzg"
        const val GET_URL_POSTFIX = "/books/v1/volumes" //

        const val BASE_URL = "https://www.googleapis.com/"
    }

}