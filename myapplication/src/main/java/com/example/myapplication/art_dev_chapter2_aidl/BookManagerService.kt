package com.example.myapplication.art_dev_chapter2_aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.os.RemoteException
import android.util.Log
import kotlinx.coroutines.delay
import org.w3c.dom.Node
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.jvm.Throws

class BookManagerService : Service() {

    companion object {
        private const val TAG = "BookManagerService"
    }

    /**
     * 这里使用CopyOnWriteArrayList是因为ADIL支持List接口，因此虽然CopyOnWriteArrayList，
     * 但是服务器依然会按照List的规范去访问数据并最终形成一个新的ArrayList给客户端
     * 类似的ConcurrentHashMap
     */
    private val mBookList = CopyOnWriteArrayList<Book>()
    private val mIsServiceDestroyed = AtomicBoolean(false)
//    private val mListenerList = CopyOnWriteArrayList<IOnNewBookArrivedListener>()
    private val mListenerList = RemoteCallbackList<IOnNewBookArrivedListener>()

    private val mBinder = object : IBookManager.Stub() {
        override fun getBook(): MutableList<Book> {
            return mBookList
        }

        override fun addBook(book: Book?) {
            mBookList += book
        }

        @Throws(RemoteException::class)
        override fun registerListener(listener: IOnNewBookArrivedListener?) {
//            if (!mListenerList.contains(listener)) {
//                mListenerList.add(listener)
//            } else {
//                Log.d(TAG, "already exits.")
//            }
            mListenerList.register(listener)
//            Log.d(TAG, "registerListener , size : ${mListenerList.size}")
        }

        @Throws(RemoteException::class)
        override fun unregisterListener(listener: IOnNewBookArrivedListener?) {
//            if (mListenerList.contains(listener)) {
//                mListenerList.remove(listener)
//            } else {
//                Log.d(TAG, "not found,can not unregister.")
//            }
            mListenerList.unregister(listener)
//            Log.d(TAG, "unregisterListener,current size:" + mListenerList.size)
        }

    }

    private inner class ServiceWorker : Runnable{
        override fun run() {
            while (!mIsServiceDestroyed.get()){
                try{
                    Thread.sleep(5000)
                }catch (e:InterruptedException){
                    e.printStackTrace()
                }
                val bookId = mBookList.size +1
                val newBook = Book(bookId.toString(),"newBook#$bookId")
                onNewBookArrived(newBook)
            }
        }
    }

    private fun onNewBookArrived(newBook: Book) {
        mBookList+=newBook
//        Log.d(TAG,"onNewBookArrived,notify listeners:" + mListenerList. size)
//        for (iOnNewBookArrivedListener in mListenerList.withIndex() ) {
//            val listener = mListenerList[iOnNewBookArrivedListener.index]
//            Log.d(TAG, "onNewBookArrived,notify listener:$listener")
//            listener.onNewBookArrived(newBook)
//        }
        val beginBroadcast = mListenerList.beginBroadcast()
        for (i in 0..beginBroadcast){
            val broadcastItem = mListenerList.getBroadcastItem(i)
            broadcastItem?.onNewBookArrived(newBook)
        }
        mListenerList.finishBroadcast()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        mBookList += Book("1", "Android开发艺术探索")
        mBookList += Book("2", "Android群英传")
        Thread(ServiceWorker()).start()
    }
}