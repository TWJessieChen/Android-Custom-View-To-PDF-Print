package com.jc666.customviewtopdfprint

import android.util.Log

class Apple {
    private val TAG = Apple::class.java.simpleName

    private var speak: String = "小啊小苹果"

    /* 主构造方法*/
    constructor(){
        Log.d(TAG, "主构造方法constructor()调用")
    }

    /* 次构造方法*/
    constructor(name:String, age:Int):this(){
        Log.d(TAG, "次带参数构造方法constructor(name:$name, age:$age)")
        speak = name
        Log.d(TAG, "次带参数构造方法constructor(speak:$speak, age:$age)")
    }

    init {
        Log.d(TAG, "Apple init")
    }

    init {
        Log.d(TAG, "Apple init：name:${speak}")
    }

    /*伴生对象*/
    companion object {
        private val TAG = Apple::class.java.simpleName
        val instance: Apple by lazy {
            Apple("wqq", 100)
        }

        /*伴生对象中的初始化代码*/
        init {
            Log.d(TAG, "companion init 1")
        }

        init {
            Log.d(TAG, "companion init 2")
        }
    }
}