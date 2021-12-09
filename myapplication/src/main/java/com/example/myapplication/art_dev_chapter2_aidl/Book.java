package com.example.myapplication.art_dev_chapter2_aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Book  implements Parcelable {

    public String bookId;
    public String bookName;

    public Book(String bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }


    protected Book(Parcel in) {
        bookId = in.readString();
        bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookId);
        dest.writeString(bookName);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
