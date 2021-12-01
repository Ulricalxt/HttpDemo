package com.example.httpdemo

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.TextView
import com.example.httpdemo.databinding.ActivityMainBinding
import com.example.httpdemo.util.HttpService
import com.example.httpdemo.util.HttpURLConnectionUtil
import com.example.httpdemo.util.HttpUtil
import com.example.httpdemo.util.XmlUtil
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var mBinder: ActivityMainBinding
    private lateinit var requestArray:Array<String>
    private val ip ="192.168.137.1"

    //private val dataTypeArray = resources.getStringArray(R.array.arr_data_)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinder.root)


        mBinder.btnGet.setOnClickListener { thread {
                val data = HttpUtil.getHttpService()
                    .get("http://$ip:8080/login?username=admin&password=123456")
                runOnUiThread {
                    mBinder.textView.text = data
                }
            } }
        mBinder.btnPost.setOnClickListener { thread {
                val data = HttpUtil.getHttpService().post(
                    "http://$ip:8080/post",
                    "input=${URLEncoder.encode("测试测试post", "UTF-8")}"
                )
                runOnUiThread {
                    mBinder.textView.text = data
                }
            } }
        mBinder.btnGetOkhttp.setOnClickListener { thread {
                try {
                    //创建OKHttp客户端对象
                    val client = OkHttpClient()

//                    val requestBody = FormBody.Builder()
//                        .add("id","input")
//                        .add("name","阿龙")
//                        .build()
                    //创建Request对象，帮助我们用来发生Http请求
                    val request = Request.Builder()
                        .url("http://$ip:8080/getKotlin?name=阿龙")
                        //.post(requestBody)
                        .build()
                    val response = client.newCall(request).execute()
                    val data = response.body?.string()
                    runOnUiThread {
                        mBinder.textView.text = data
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } }
        mBinder.btnPostOkhttp.setOnClickListener { thread {
                try {
                    //创建OKHttp客户端对象
                    val client = OkHttpClient()

                    val requestBody = FormBody.Builder()
                        .add("id","input")
                        .add("name","阿龙")
                        .build()
                    //创建Request对象，帮助我们用来发生Http请求
                    val request = Request.Builder()
                        .url("http://$ip:8080/post/student")
                        .post(requestBody)
                        .build()
                    val response = client.newCall(request).execute()
                    val data = response.body?.string()
                    runOnUiThread {
                        mBinder.textView.text = data
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } }
        mBinder.btnXmlPull.setOnClickListener { thread {
                try {
                    //创建OKHttp客户端对象
                    val client = OkHttpClient()
                    //创建Request对象，帮助我们用来发生Http请求
                    val request = Request.Builder()
                        .url("http://$ip:8080/xml/get_data.xml")
                        //login?username=admin&password=123456
                        .build()
                    val response = client.newCall(request).execute()
                    val data = response.body?.string()
                    runOnUiThread {
                        mBinder.textView.text = XmlUtil.parseXMLWithPull(data)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } }
        mBinder.btnXmlSax.setOnClickListener { thread {
                try {
                    //创建OKHttp客户端对象
                    val client = OkHttpClient()
                    //创建Request对象，帮助我们用来发生Http请求
                    val request = Request.Builder()
                        .url("http://$ip:8080/xml/get_data.xml")
                        //login?username=admin&password=123456
                        .build()
                    val response = client.newCall(request).execute()
                    val data = response.body?.string()
                    runOnUiThread {
                        mBinder.textView.text = XmlUtil.parseXMLWithSAX(data)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } }
        mBinder.btnJsonGson.setOnClickListener {  }
        mBinder.btnJsonJsonObject.setOnClickListener {  }
    }


}

//    private fun sendRequestWithHttpURLConnection() {
//        //开启线程，发起网络请求
//        thread {
//            var connection: HttpURLConnection? = null
//            try {
//                //根据网址创建URL对象
//                val url = URL("http://:8080/login=admin&password=123456")
//                //打开网址连接
//                connection = url.openConnection() as HttpURLConnection
//                //设置连接超时
//                connection.connectTimeout = 8000
//                //设置读取超时
//                connection.readTimeout = 8000
//                //设置请求方式为post(默认GET)
//                connection.requestMethod = "POST"
//
//                val output = DataOutputStream(connection.outputStream)
//
//                output.writeBytes("username=admin&password=123456")
//                //获取网络请求(服务器返回)的输入流
//                val input = connection.inputStream
//                //对输入流进行读取
//                val reader = BufferedReader(InputStreamReader(input))
//                //将读到的数据拼接为字符串
//                val stringBuilder = StringBuilder()
//                reader.use {
//                    reader.forEachLine {
//                        stringBuilder.append(it)
//                    }
//                }
//                //将字符串渲染到界面上
//                showTtxt(stringBuilder.toString())
//            }catch (e:Exception){
//                e.printStackTrace()
//            }finally {
//                //将链接关闭
//                connection?.disconnect()
//            }
//        }
//    }



