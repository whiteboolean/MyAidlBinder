package com.example.myapplication.art_dev_chapter2_aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMain4Binding

class MainActivity : AppCompatActivity(), Handler.Callback {

    companion object {
        private const val TAG = "MainActivity"
        private const val MESSAGE_NEW_BOOK_ARRIVED: Int = 1
    }

    lateinit var viewRoot: ActivityMain4Binding
    lateinit var mHandler: Handler
    private var iBookBinder: IBookManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewRoot = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(viewRoot.root)
        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            Log.i(TAG, "start to bind bookService")
            Toast.makeText(this, "绑定服务", Toast.LENGTH_SHORT).show()
            bindBookService()
        }

        viewRoot.button3.setOnClickListener {
            iBookBinder?.apply {
                val bookList = book
                Log.i(TAG, "query book list:$bookList")
                val newBook = Book("3", "第一行代码")
                addBook(newBook)
                Log.i(TAG, "add book:$newBook")
                Log.i(TAG, "query book list:$book")
                viewRoot.textView.text = book.toString()
                registerListener(mIOnNewBookArrivedListener)
            }
        }

        mHandler = Handler(Looper.getMainLooper(), this)


    }

    private val mIOnNewBookArrivedListener = object : IOnNewBookArrivedListener.Stub() {
        override fun onNewBookArrived(newBook: Book?) {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget()
        }

    }


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iBookBinder = IBookManager.Stub.asInterface(service)
            Toast.makeText(this@MainActivity, "服务绑定成功", Toast.LENGTH_SHORT).show()
//第一次演示的例子
//            iBookBinder?.apply {
//                val bookList:List<Book>? = book
//                Log.i(TAG,"query book list ,list type : ${bookList?.javaClass?.canonicalName}")
//                Log.i(TAG,"query book list: ${bookList.toString()}" )
//            }

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            iBookBinder = null
        }

    }

    private fun bindBookService() {
        bindService(
            Intent(this, BookManagerService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        if (iBookBinder != null && iBookBinder!!.asBinder().isBinderAlive) {
            iBookBinder!!.unregisterListener(mIOnNewBookArrivedListener)
        }

        unbindService(connection)


    }

    override fun handleMessage(msg: Message): Boolean {
        when(msg.what){
            MESSAGE_NEW_BOOK_ARRIVED ->{
                Log.d(TAG, "handleMessage: receive new book:$msg.obj")
            }
        }
        return false
    }

}