// IOnNewBookArrivedListener.aidl
package com.example.myapplication.art_dev_chapter2_aidl;
import com.example.myapplication.art_dev_chapter2_aidl.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}