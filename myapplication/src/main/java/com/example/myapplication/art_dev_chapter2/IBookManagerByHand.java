package com.example.myapplication.art_dev_chapter2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IBookManagerByHand extends IInterface {

    String DESCRIPTOR = "com.example.myapplication.art_dev_chapter2.IBookManagerByHand";

    int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;

    int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    List<Book> getBookList() throws RemoteException;

    void addBook(Book book) throws RemoteException;

    public class BookManagerImp extends Binder implements IBookManagerByHand {

        public BookManagerImp() {
            this.attachInterface(this, DESCRIPTOR);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_getBookList: {
                    data.enforceInterface(DESCRIPTOR);
                    List<Book> result = this.getBookList();
                    reply.writeNoException();
                    reply.writeTypedList(result);
                    return true;
                }
                case TRANSACTION_addBook: {
                    data.enforceInterface(DESCRIPTOR);
                    Book arg0;
                    if ((0 != data.readInt())) {
                        arg0 = Book.CREATOR.createFromParcel(data);
                    } else {
                        arg0 = null;
                    }
                    this.addBook(arg0);
                    reply.writeNoException();
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        public static IBookManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof IBookManager))) {
                return ((IBookManager) iin);
            }
            return new BookManagerImp.Proxy(obj);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return null;
        }

        @Override
        public void addBook(Book book) throws RemoteException {

        }

        @Override
        public IBinder asBinder() {
            return this;
        }


        private static class Proxy implements IBookManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override
            public List<Book> getBook() throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                List<Book> result;
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_getBookList, data, reply, 0);
                    reply.readException();
                    result = reply.createTypedArrayList(Book.CREATOR);
                } finally {
                    reply.recycle();
                    data.recycle();
                }
                return result;
            }

            @Override
            public void addBook(Book book) throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    if ((book != null)) {
                        data.writeInt(1);
                        book.writeToParcel(data, 0);
                    } else {
                        data.writeInt(0);
                    }
                    mRemote.transact(TRANSACTION_addBook, data, reply, 0);
                    reply.readException();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }
        }

//        /**
//         * 两个Binder很重要的方法
//         * 1.linkToDeath
//         * 2.unLinkToDeath
//         */
//        private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){
//
//            @Override
//            public void binderDied() {
//                if (mBookManager == null){
//                 mBook
//                }
//            }
//        };
    }


}
