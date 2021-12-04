package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GradeService extends Service {
    public static final int REQUEST_CODE=1000;

    private final Binder mBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            if (code == REQUEST_CODE) {
                String name = data.readString();
                // 根据姓名查询学生成绩并将成绩写入到返回数据
//                int studentGrade = getStudentGrade(name);
                if (reply != null)
//                    reply.writeInt(studentGrade);
                return true;
            }
            return super.onTransact(code, data, reply, flags);
        }
        // 根据姓名查询学生成绩
        public void getStudentGrade(String name) {
//            return StudentMap.getStudentGrade(name);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}