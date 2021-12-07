package com.example.myapplication.art_dev_chapter2;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";
    private final Messenger messenger = new Messenger(new MessengerHandler(Looper.getMainLooper()));
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    public static class MessengerHandler extends Handler{
        public MessengerHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    Log.e(TAG,"哈哈我接收到了数据");
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }
}
