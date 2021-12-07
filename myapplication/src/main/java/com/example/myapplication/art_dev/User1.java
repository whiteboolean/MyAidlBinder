package com.example.myapplication.art_dev;

import android.os.Parcel;
import android.os.Parcelable;

public class User1 implements Parcelable {
    public int userId;
    public String userName;
    public boolean isMale;
    public Book book;

    public User1(int userId, String userName, boolean isMale, Book book) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
        this.book = book;
    }
    protected User1(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readByte() != 0;
        book = in.readParcelable(Book.class.getClassLoader());
    }

    public static final Creator<User1> CREATOR = new Creator<User1>() {
        @Override
        public User1 createFromParcel(Parcel in) {
            return new User1(in);
        }

        @Override
        public User1[] newArray(int size) {
            return new User1[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 完成序列化
     * @param out
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(userId);
        out.writeString(userName);
        out.writeInt(isMale ? 1 : 0);
        out.writeParcelable(book,0);
    }
}
