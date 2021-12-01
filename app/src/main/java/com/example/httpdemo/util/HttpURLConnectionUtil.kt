package com.example.httpdemo.util

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object HttpURLConnectionUtil:HttpService {

    override fun get(urlString: String): String {
        //将读到的数据拼接为字符串
        val stringBuilder = StringBuilder()
        var connection: HttpURLConnection? = null
        //开启线程，发起网络请求


        try {
            //根据网址创建URL对象
            val url = URL(urlString)
            //打开网址连接
            connection = url.openConnection() as HttpURLConnection
            //设置连接超时
            connection.connectTimeout = 8000
            //设置读取超时
            connection.readTimeout = 8000

            //获取网络请求(服务器返回)的输入流
            val input = connection.inputStream
            //对输入流进行读取
            val reader = BufferedReader(InputStreamReader(input))

            reader.use {
                reader.forEachLine {
                    stringBuilder.append(it)
                }
            }
            //将字符串渲染到界面上
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            //将链接关闭
            connection?.disconnect()
        }


    return stringBuilder.toString()
}
    override fun post(urlString: String,parameter:String): String{
        //将读到的数据拼接为字符串
        val stringBuilder = StringBuilder()
        var connection: HttpURLConnection? = null
        //开启线程，发起网络请求
            try {
                //根据网址创建URL对象
                val url = URL(urlString)
                //打开网址连接
                connection = url.openConnection() as HttpURLConnection
                //设置连接超时
                connection.connectTimeout = 8000
                //设置读取超时
                connection.readTimeout = 8000
                //设置请求方式为post(默认GET)
                connection.requestMethod = "POST"

                val output = DataOutputStream(connection.outputStream)

                output.writeBytes(parameter)

                //获取网络请求(服务器返回)的输入流
                val input = connection.inputStream
                //对输入流进行读取
                val reader = BufferedReader(InputStreamReader(input))

                reader.use {
                    reader.forEachLine {
                        stringBuilder.append(it)
                    }
                }
                //将字符串渲染到界面上
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                //将链接关闭
                connection?.disconnect()
            }
        return stringBuilder.toString()
    }

}