package com.example.httpdemo.util


interface HttpService {
    //enum class Type{ GET,POST }
    //var data: String?


    fun get(urlString: String ): String
    fun post(urlString: String,parameter:String ): String


}