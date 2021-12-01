package com.example.httpdemo

import android.util.Log
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class MyHandler:DefaultHandler() {

    private var nodeName=""
    private lateinit var id:StringBuilder
    private lateinit var name:StringBuilder
    private lateinit var version:StringBuilder
    private lateinit var data: StringBuilder

    //文档开始的地方
    override fun startDocument() {
        id = StringBuilder()
        name = StringBuilder()
        version = StringBuilder()
        data =StringBuilder()
    }
    //节点开始的地方
    override fun startElement(uri: String, localName: String, qName: String,
                              attributes: Attributes?) {
        nodeName= localName
        Log.d("TAG","uri is $uri")
        Log.d("TAG","localName is $localName")
        Log.d("TAG","qName is $qName")
        Log.d("TAG","attributes is $attributes")
    }
    override fun characters(ch: CharArray, start: Int, length: Int) {
        when(nodeName){
            "id" -> id.append(ch,start,length)
            "name" -> name.append(ch,start,length)
            "version" -> version.append(ch,start,length)
        }
    }
    //节点结束的地方
    override fun endElement(uri: String, localName: String, qName: String) {
        if ("app" == localName){
            data.append("APP编号：${id.trim()}")
                .append('\n')
                .append("APP名字：${name.trim()}")
                .append('\n')
                .append("APP版本：${version.trim()}")
                .append('\n')
                .append('\n')

            id.setLength(0)
            name.setLength(0)
            version.setLength(0)
        }
    }
//文档结束的地方
    override fun endDocument() {}

    fun getData() = data.toString()

}